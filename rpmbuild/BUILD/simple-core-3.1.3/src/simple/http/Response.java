/*
 * Response.java February 2001
 *
 * Copyright (C) 2001, Niall Gallagher <niallg@users.sf.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General 
 * Public License along with this library; if not, write to the 
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, 
 * Boston, MA  02111-1307  USA
 */
 
package simple.http;

import java.net.InetAddress;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;

/**
 * This is used to represent the HTTP response. This provides methods 
 * that can be used to set various characteristics of the response.
 * <p>
 * The <code>OutputStream</code> of the <code>Response</code> can be 
 * retrieved from this interface as can the I.P address of the client 
 * that will be receiving the <code>Response</code>. The attributes 
 * of the connection can be retrieved also. This provides a set of 
 * methods that can be used to set the attributes of the stream so 
 * the <code>Response</code> can be transported properly. The headers
 * can be set and will be sent once a commit is made.
 * <p>
 * This should never allow the message body be sent if it should not 
 * be sent with the headers as of RFC 2616 rules for the presence of 
 * a message body. A message body must not be included with a HEAD 
 * request or with a 304 or a 204 response. A proper implementation
 * of this will prevent a message body being sent if the response
 * is to a HEAD request of if there is a 304 or 204 response code.
 * <p>
 * It is important to note that the <code>Response</code> controls
 * the processing of the HTTP pipeline. The next HTTP request is 
 * not processed until the response has committed. The response is
 * committed once the <code>commit</code> method is invoked if there
 * is NO content body. Committing with a content body is done only if
 * correct content is given. The <code>OutputStream</code> acts as 
 * a client and commits the response once the specified content has 
 * been written to the issued <code>OutputStream</code>.
 *
 * @author Niall Gallagher
 */ 
public interface Response extends StatusLine, GenericHeader { 

   /**
    * This should be used when the size of the message body is known. For 
    * performance reasons this should be used so the length of the output
    * is known. This ensures that Persistent HTTP (PHTTP) connections 
    * can be maintained for both HTTP/1.0 and HTTP/1.1 clients. If the
    * length of the output is not known HTTP/1.0 clients will require a
    * connection close, which reduces performance (see RFC 2616).
    * <p>
    * This removes any previous Content-Length headers from the message 
    * header. This will then set the appropriate Content-Length header with
    * the correct length. If a the Connection header is set with the close
    * token then the semantics of the connection are such that the server
    * will close it once the <code>OutputStream.close</code> is used.
    *
    * @param length this is the length of the HTTP message body
    */ 
   public void setContentLength(int length);

   /**
    * This can be used to retrieve certain attributes about
    * this <code>Response</code>. The attributes contains certain
    * properties about the <code>Response</code>. For example if
    * this Response goes over a secure line then there may be any
    * arbitrary attributes.
    *
    * @return the attributes of this <code>Response</code> object
    */ 
   public Attributes getAttributes();

   /**
    * This is used as a shortcut for acquiring attributes for the
    * response. This avoids acquiring the <code>Attributes</code>
    * in order to retrieve the attribute directly from that object.
    * The attributes contain data specific to the response.
    * 
    * @param name this is the name of the attribute to acquire
    * 
    * @return this returns the attribute for the specified name
    */ 
   public Object getAttribute(String name);
   
   /**
    * This can be used to get the I.P address for the browser that
    * the <code>Response</code> goes to. The <code>Attributes</code> 
    * shares this information. This method is used to that objects
    * can determine, based on the retrieved I.P address, what type
    * of output is suitable. Statistics such as location can be 
    * determined based on the DNS address obtained.
    *
    * @return returns the source I.P. address of the client
    */ 
   public InetAddress getInetAddress();
   
   /**
    * Used to write a message body with the <code>Response</code>. The 
    * semantics of this <code>OutputStream</code> will be determined 
    * by the HTTP version of the client, and whether or not the content
    * length has been set, through the <code>setContentLength</code>
    * method. If the length of the output is not known then the output
    * is chunked for HTTP/1.1 clients and closed for HTTP/1.0 clients.
    * The <code>OutputStream</code> issued must be thread safe so that 
    * it can be used in a concurrent environment.
    *
    * @exception IOException this is thrown if there was an I/O error
    *
    * @return an <code>OutputStream</code> with the specified semantics
    */ 
   public OutputStream getOutputStream() throws IOException; 

   /**
    * Used to write a message body with the <code>Response</code>. The 
    * semantics of this <code>OutputStream</code> will be determined 
    * by the HTTP version of the client, and whether or not the content
    * length has been set, through the <code>setContentLength</code>
    * method. If the length of the output is not known then the output
    * is chunked for HTTP/1.1 clients and closed for HTTP/1.0 clients.
    * The <code>OutputStream</code> issued must be thread safe so that 
    * it can be used in a concurrent environment.   
    * <p>
    * This will ensure that there is buffering done so that the output
    * can be reset using the <code>reset</code> method. This will 
    * enable the specified number of bytes to be written without
    * committing the response. This specified size is the minimum size
    * that the response buffer must be. 
    *
    * @param size the minimum size that the response buffer must be
    *
    * @exception IOException this is thrown if there was an I/O error
    *
    * @return an <code>OutputStream</code> with the specified semantics
    */ 
   public OutputStream getOutputStream(int size) throws IOException;

   /**
    * This method is provided for convenience so that the HTTP content
    * can be written using the <code>print</code> methods provided by
    * the <code>PrintStream</code>. This will basically wrap the 
    * <code>getOutputStream</code> with a buffer size of zero.
    * <p>
    * The retrieved <code>PrintStream</code> uses the charset used to
    * describe the content, with the Content-Type header. This will
    * check the charset parameter of the contents MIME type. So if 
    * the Content-Type was <code>text/plain; charset=UTF-8</code> the
    * resulting <code>PrintStream</code> would encode the written data
    * using the UTF-8 encoding scheme. Care must be taken to ensure
    * that bytes written to the stream are correctly encoded.
    * <p> 
    * Implementations of the <code>Response</code> must guarantee
    * that this can be invoked repeatedly without effecting any issued 
    * <code>OutputStream</code> or <code>PrintStream</code> object.
    *
    * @return a <code>PrintStream</code> that provides convenience
    * methods to the <code>Response</code> for writing content
    *
    * @exception IOException this is thrown if there was an I/O error
    */
   public PrintStream getPrintStream() throws IOException;

   /**
    * This method is provided for convenience so that the HTTP content
    * can be written using the <code>print</code> methods provided by
    * the <code>PrintStream</code>. This will basically wrap the 
    * <code>getOutputStream</code> with a specified buffer size.
    * <p>
    * The retrieved <code>PrintStream</code> uses the charset used to
    * describe the content, with the Content-Type header. This will
    * check the charset parameter of the contents MIME type. So if 
    * the Content-Type was <code>text/plain; charset=UTF-8</code> the
    * resulting <code>PrintStream</code> would encode the written data
    * using the UTF-8 encoding scheme. Care must be taken to ensure
    * that bytes written to the stream are correctly encoded.
    * <p> 
    * Implementations of the <code>Response</code> must guarantee
    * that this can be invoked repeatedly without effecting any issued 
    * <code>OutputStream</code> or <code>PrintStream</code> object.
    *
    * @param size the minimum size that the response buffer must be
    *
    * @return a <code>PrintStream</code> that provides convenience
    * methods to the <code>Response</code> for writing content
    *
    * @exception IOException this is thrown if there was an I/O error
    */
   public PrintStream getPrintStream(int size) throws IOException;

   /**
    * This is used to write the headers that where given to the
    * <code>Response</code>. Any further attempts to give headers 
    * to the <code>Response</code> will be futile as only the headers
    * that were given at the time of the first commit will be used 
    * in the message header.
    * <p>
    * This also performs some final checks on the headers submitted.
    * This is done to determine the optimal performance of the 
    * output. If no specific Connection header has been specified
    * this will set the connection so that HTTP/1.0 closes by default.
    *
    * @exception IOException thrown if there was a problem writing
    */
   public void commit() throws IOException;

   /**
    * This can be used to determine whether the <code>Response</code>
    * has been committed. This is true if the <code>Response</code> 
    * was committed, either due to an explicit invocation of the
    * <code>commit</code> method or due to the writing of content. If
    * the <code>Response</code> has committed the <code>reset</code> 
    * method will not work in resetting content already written.
    *
    * @return true if the <code>Response</code> has been committed 
    */ 
   public boolean isCommitted();

   /**
    * The <code>reset</code> method is used to reset the output 
    * from an issued <code>OutputStream</code>. This will not work
    * is the <code>isCommitted</code> returns true. If the streams
    * byte buffer overflows the response will commit and the
    * <code>reset</code> will fail.
    */
   public void reset();

   /** 
    * This constructs the HTTP message header according to the 
    * format of RFC 2616. This returns a <code>String</code> that 
    * contains each header formatted according to the HTTP/1.1 
    * header format. The header will contain the status line 
    * followed by each header and ended with the CRLF.
    *
    * @return the HTTP response header in string format
    */
   public String toString();
}
