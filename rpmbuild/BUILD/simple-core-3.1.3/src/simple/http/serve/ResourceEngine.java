/*
 * ResourceEngine.java February 2001
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
 
package simple.http.serve;

/**
 * The <code>ResourceEngine</code> is used to create implementations
 * of the <code>Resource</code> interface that suit the targeted
 * resource. Different <code>Resource</code> objects may be needed to 
 * handle different files/directories or even applications. The request
 * URI specified must be a HTTP request URI as of RFC 2616. 
 * <p>
 * The meaning of HTTP URI in this instance is the request URI
 * from a HTTP/x.x request, as RFC 2616 and RFC 2396 defines it
 *
 * <pre> 
 * Request-Line = Method SP Request-URI SP HTTP-Version CRLF
 *
 * Request-URI = "*" | absoluteURI | abs_path | authority
 * absoluteURI = "http:" "//" host [":" port] [abs_path ["?" query]] 
 * abs_path = "/" path_segments         
 * path_segments = segment *( "/" segment )
 * </pre> 
 *
 * The <code>ResourceEngine</code> object must be prepared to accept 
 * the request URI that come in the form outlined above. These can
 * include formats like 
 *
 * <pre> 
 * http://some.host/pub;param=value/bin/index.html?name=value
 * http://some.host:8080/index.en_US.html
 * some.host:8080/index.html
 * /usr/bin;param=value/README.txt
 * /usr/bin/compress.tar.gz
 * </pre>
 *
 * The <code>ResourceEngine</code> implementation should be able to 
 * directly take a Request-URI as defined in RFC 2616 and translate 
 * this into a <code>Resource</code>. This keeps the objects semantics 
 * simple and explicit, although at the expense of performance.
 * <p>
 * The <code>Resource</code> returned is an implementation of the
 * <code>ProtocolHandler</code> interface. Implementation's of this
 * can acquire the <code>Resource</code> based on the URI or query.
 *
 * @author Niall Gallagher
 */
public interface ResourceEngine {

   /**
    * This will look for and retrieve the requested resource. The 
    * target given must be in the form of a request URI. This will
    * locate the resource and return the <code>Resource</code>
    * implementation that will handle the target.
    *
    * @param target the URI style path that represents the target 
    * <code>Resource</code>
    *
    * @return this returns the <code>Resource</code> object to
    * handle the desired target
    *
    * @throws IllegalArgumentException if the path given is not 
    * relative URI style
    */
   public Resource resolve(String target);
}

