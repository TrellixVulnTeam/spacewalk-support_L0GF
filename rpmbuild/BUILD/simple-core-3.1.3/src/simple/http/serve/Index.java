/*
 * Index.java December 2005
 *
 * Copyright (C) 2005, Niall Gallagher <niallg@users.sf.net>
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

import simple.util.net.Path;
import java.util.Locale;
import java.io.File;

/**
 * The <code>Index</code> object is used to represent the properties 
 * a URI can contain. This is used so that properties relating to a
 * file can be quickly extracted from an <code>Indexer</code>. This
 * will contain all necessary meta data for a file or resource. With
 * this the <code>File</code> reference to a resource as well as the
 * locale, MIME type, name  and other such data can be accessed.
 * 
 * @author Niall Gallagher
 *
 * @see simple.http.serve.Indexer
 */ 
public interface Index {

   /**
    * This allows the name for this object to be acquired. The
    * name usually refers to the last entry in the path. So if
    * the index target path was "/usr/bin/" the name is "bin".
    *
    * @return this returns the name of this index target
    */         
   public String getName();
   
   /**
    * This allows the MIME type of this <code>Index</code> to
    * be acquired. The MIME type of a file is retrieved by the
    * <code>Context.getContentType</code> method for a specific
    * request URI. This should have a value and perhaps some
    * parameters like the charset, "text/html; charset=UTF-8".
    *
    * @return the MIME type this object has been set to
    */    
   public String getContentType();
   
   /**
    * This gets the locale for this index object the locale is
    * set to the <code>Locale.getDefault</code> if there is no
    * locale information available for the index target. This
    * will provide the <code>Context.getLocale</code> object.
    *
    * @return this returns the locale for this index target
    */ 
   public Locale getLocale();

   /**
    * This is used to get the path that this object refers to. 
    * This should be the fully qualified normalized path. This
    * refers to the OS system specific path that this represents.
    *
    * @return this returns the OS specific path for the target
    */    
   public String getRealPath();

   /**
    * This is used to acquire the normalized URI style path for
    * the index target. This allows the path to be used within
    * the <code>Mapper</code> and other such objects that need
    * a normalized URI style path to resolve resources.
    *
    * @return this returns the normalized path for the target
    */ 
   public String getRequestPath();

   /**
    * This is used to acquire the <code>File</code> directory
    * for the index target. This is typically rooted at a
    * base path, for instance the <code>Context</code> root
    * is typically used. This allows resources within the 
    * same directory to be acquired easily.
    *
    * @return this returns the OS file for the directory
    */ 
   public File getDirectory();
   
   /**
    * This is used to acquire the <code>File</code> reference
    * for the index target. This is typically rooted at a
    * base path, for instance the <code>Context</code> root
    * is typically used. This allows the file to be opened,
    * deleted, or read should the need arise in a service.
    *
    * @return this returns the OS file for the resource
    */ 
   public File getFile();

   /**
    * This is used to acquire the <code>Path</code> object that 
    * exposes various parts of the URI path. This can be used 
    * to extract the individual path segments as strings as 
    * well as the file extension and various other details.
    *
    * @return this returns a path object with various details
    */ 
   public Path getPath();
}
