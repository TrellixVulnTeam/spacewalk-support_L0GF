/*
 * ParseBuffer.java February 2001
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
 
package simple.util.parse;

import java.io.Serializable;

/** 
 * This is primarily used to replace the <code>StringBuffer</code> 
 * class, as a way for the <code>Parser</code> to store the char's
 * for a specific region within the parse data that constitutes a 
 * desired value. The methods are not synchronized so it enables 
 * the <code>char</code>'s to be taken quicker than the 
 * <code>StringBuffer</code> class.
 *
 * @author Niall Gallagher
 */
public class ParseBuffer implements Serializable {      

   /** 
    * This is used to quicken <code>toString</code>.
    */
   protected String cache;

   /** 
    * The <code>char</code>'s this buffer accumulated.
    */
   protected char[] buf;

   /** 
    * This is the number of <code>char</code>'s stored.
    */
   protected int count;
   
   /** 
    * Constructor for <code>ParseBuffer</code>. The default 
    * <code>ParseBuffer</code> stores 16 <code>char</code>'s 
    * before a <code>resize</code> is needed to accomodate
    * extra characters. 
    */
   public ParseBuffer(){
      this(16);
   }
   
   /** 
    * This creates a <code>ParseBuffer</code> with a specific 
    * default size. The buffer will be created the with the 
    * length specified. The <code>ParseBuffer</code> can grow 
    * to accomodate a collection of <code>char</code>'s larger 
    * the the size spacified.
    *
    * @param size initial size of this <code>ParseBuffer</code>
    */
   public ParseBuffer(int size){
      this.buf = new char[size];
   }
   
   /** 
    * This will add a <code>char</code> to the end of the buffer.
    * The buffer will not overflow with repeated uses of the 
    * <code>append</code>, it uses an <code>ensureCapacity</code>
    * method which will allow the buffer to dynamically grow in 
    * size to accomodate more <code>char</code>'s.
    *
    * @param c the <code>char</code> to be appended
    */
   public void append(char c){
      ensureCapacity(count+ 1);
      buf[count++] = c;
   }

   /** 
    * This will add a <code>String</code> to the end of the buffer.
    * The buffer will not overflow with repeated uses of the 
    * <code>append</code>, it uses an <code>ensureCapacity</code> 
    * method which will allow the buffer to dynamically grow in 
    * size to accomodate large <code>String</code> objects.
    *
    * @param str the <code>String</code> to be appended to this
    */  
   public void append(String str){
      ensureCapacity(count+ str.length());
      str.getChars(0,str.length(),buf,count);
      count += str.length();
   }

   /** 
    * This will add a <code>ParseBuffer</code> to the end of this.
    * The buffer will not overflow with repeated uses of the 
    * <code>append</code>, it uses an <code>ensureCapacity</code> 
    * method which will allow the buffer to dynamically grow in 
    * size to accomodate large <code>ParseBuffer</code> objects.
    *
    * @param text the <code>ParseBuffer</code> to be appended 
    */  
   public void append(ParseBuffer text){
      append(text.buf, 0, text.count);           
   }
   
   /** 
    * This will add a <code>char</code> to the end of the buffer.
    * The buffer will not overflow with repeated uses of the 
    * <code>append</code>, it uses an <code>ensureCapacity</code> 
    * method which will allow the buffer to dynamically grow in 
    * size to accomodate large <code>char</code> arrays.
    *
    * @param c the <code>char</code> array to be appended to this
    * @param off the read offset for the array    
    * @param len the number of <code>char</code>'s to add
    */   
   public void append(char[] c, int off, int len){
      ensureCapacity(count+ len);
      System.arraycopy(c,off,buf,count,len);
      count+=len;
   }
   
   /** 
    * This will add a <code>String</code> to the end of the buffer.
    * The buffer will not overflow with repeated uses of the 
    * <code>append</code>, it uses an <code>ensureCapacity</code>
    * method which will allow the buffer to dynamically grow in 
    * size to accomodate large <code>String</code> objects.
    *
    * @param str the <code>String</code> to be appended to this
    * @param off the read offset for the <code>String</code>
    * @param len the number of <code>char</code>'s to add
    */   
   public void append(String str, int off, int len){
      ensureCapacity(count+ len);
      str.getChars(off,len,buf,count);  
      count += len;
   }


   /** 
    * This will add a <code>ParseBuffer</code> to the end of this.
    * The buffer will not overflow with repeated uses of the 
    * <code>append</code>, it uses an <code>ensureCapacity</code> 
    * method which will allow the buffer to dynamically grow in 
    * size to accomodate large <code>ParseBuffer</code> objects.
    *
    * @param text the <code>ParseBuffer</code> to be appended 
    * @param off the read offset for the <code>ParseBuffer</code>
    * @param len the number of <code>char</code>'s to add
    */  
   public void append(ParseBuffer text, int off, int len){
      append(text.buf, off, len);           
   }   
   
   /** 
    * This ensure that there is enough space in the buffer to 
    * allow for more <code>char</code>'s to be added. If
    * the buffer is already larger than min then the buffer 
    * will not be expanded at all.
    *
    * @param min the minimum size needed
    */     
   protected void ensureCapacity(int min) {
      if(buf.length < min) {
         int size = buf.length * 2;
         int max = Math.max(min, size);
         char[] temp = new char[max];         
         System.arraycopy(buf, 0, temp, 0, count); 
         buf = temp;
      }
   }  
   
   /** 
    * This will empty the <code>ParseBuffer</code> so that the
    * <code>toString</code> paramater will return <code>null</code>. 
    * This is used so that the same <code>ParseBuffer</code> can be 
    * recycled for different tokens.
    */
   public void clear(){
      cache = null;
      count = 0;
   }
  
   /** 
    * This will return the number of bytes that have been appended 
    * to the <code>ParseBuffer</code>. This will return zero after 
    * the clear method has been invoked.
    *
    * @return the number of <code>char</code>'s within the buffer
    */
   public int length(){
      return count;
   }

   /** 
    * This will return the characters that have been appended to the 
    * <code>ParseBuffer</code> as a <code>String</code> object.
    * If the <code>String</code> object has been created before then
    * a cached <code>String</code> object will be returned. This
    * method will return <code>null</code> after clear is invoked.
    *
    * @return the <code>char</code>'s appended as a <code>String</code>
    */
   public String toString(){
      if(count <= 0) {
         return null;
      }
      if(cache != null) {
         return cache;
      }
      cache = new String(buf,0,count);
      return cache;
   }
}   
