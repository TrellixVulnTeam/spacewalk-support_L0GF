/*
 * Parser.java February 2001
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
 * This <code>Parser</code> object is to be used as a simple template 
 * for parsing uncomplicated expressions. This object is used to parse 
 * a <code>String</code>. This provides a few methods that can be used 
 * to store and track the reading of data from a buffer. There are two 
 * abstract methods provided to allow this to be subclassed to create 
 * a <code>Parser</code> for a given <code>String</code>.
 *
 * @author Niall Gallagher
 */
public abstract class Parser implements Serializable {

   /** 
    * This is the buffer that is being parsed.
    */
   protected char[] buf;

   /** 
    * This represents the current read offset.
    */
   protected int off;   

   /** 
    * This represents the length of the buffer. 
    */
   protected int count;
  
   /** 
    * This is a no argument constructor for the <code>Parser</code>. 
    * This will be invoked by each subclass of this object. It will 
    * set the buffer to a zero length buffer so that when the 
    * <code>ensureCapacity</code> method is used the buf's 
    * length can be checked.
    */
   protected Parser(){
      this.buf = new char[0];
   }
   
   /** 
    * This is used to parse the <code>String</code> given to it. This 
    * will ensure that the <code>char</code> buffer has enough space 
    * to contain the characters from the <code>String</code>. This 
    * will firstly ensure that the buffer is resized if nessecary. The 
    * second step in this <code>parse</code> method is to initialize 
    * the <code>Parser</code> object so that multiple parse invokations 
    * can be made. The <code>init</code> method will reset this to an 
    * prepared state. Then finally the <code>parse</code> method is 
    * called to parse the <code>char</code> buffer.
    *
    * @param text the <code>String</code> to be parsed with this 
    * <code>Parser</code>
    */    
   public void parse(String text){
      if(text != null){ 
         ensureCapacity(text.length());
         count = text.length();
         text.getChars(0, count, buf,0);
         init(); 
         parse();
      }
   }
   
   /** 
    * This ensure that there is enough space in the buffer to allow 
    * for more <code>char</code>'s to be added. If the buffer is 
    * already larger than min then the buffer will not be expanded 
    * at all.
    *
    * @param min the minimum size needed to accomodate the characters
    */     
   protected void ensureCapacity(int min) {
      if(buf.length < min) {
         int size = buf.length * 2;
         int max = Math.max(min, size);
         char[] temp = new char[max];         
         buf = temp;
      }
   }
   
   /** 
    * This is used to determine if a given ISO-8859-1 character is 
    * a space character. That is a whitespace character this sees 
    * the, space, carrage return and line feed characters as
    * whitespace characters.
    *
    * @param c the character that is being determined by this
    *
    * @return true if the character given it is a space character
    */
   protected boolean space(char c) {
      switch(c){
      case ' ': case '\t':
      case '\n': case '\r':
         return true;
      default:
         return false;
      }
   } 
   
   /** 
    * This is used to determine wheather or not a given character is 
    * a digit character. It assumes iso-8859-1 encoding to compare.
    *
    * @param c the character being determined by this method
    *
    * @return true if the character given is a digit character
    */      
   protected boolean digit(char c){
      return c <= '9' && '0' <= c;
   }
   
   /** 
    * This takes a unicode character and assumes an encoding of 
    * ISO-8859-1. This then checks to see if the given character 
    * is uppercase if it is it converts it into is ISO-8859-1 
    * lowercase char.
    *
    * @param c the <code>char</code> to be converted to lowercase
    *
    * @return the lowercase ISO-8859-1 of the given character
    */
   protected char toLower(char c) {
      if(c >= 'A' && c <= 'Z') {
         return (char)((c - 'A') + 'a');
      }
      return c;
   }

   /** This is used to skip an arbitrary <code>String</code> within the
    * <code>char</code> buf. It checks the length of the <code>String</code> 
    * first to ensure that it will not go out of bounds. A comparison 
    * is then made with the buffers contents and the <code>String</code>
    * if the reigon in the buffer matched the <code>String</code> then the
    * offset within the buffer is increased by the <code>String</code>'s
    * length so that it has effectively skipped it.   
    *
    * @param text this is the <code>String</code> value to be skipped
    *
    * @return true if the <code>String</code> was skipped
    */
   protected boolean skip(String text){
      int size = text.length();
      int read = 0;
      
      if(off + size > count){
         return false;
      }
      while(read < size){
         char a = text.charAt(read);
         char b = buf[off + read++];
         
         if(toLower(a) != toLower(b)){            
            return false;
         }
      }
      off += size;
      return true;
   }

   /** 
    * This will initialize the <code>Parser</code> when it is ready 
    * to parse a new <code>String</code>. This will reset the 
    * <code>Parser</code> to a ready state. The <code>init</code> 
    * method is invoked by the <code>Parser</code> when the 
    * <code>parse</code> method is invoked.
    */
   protected abstract void init();
   
   /** 
    * This is the method that should be implemented to read 
    * the buf. This method should attempt to extract tokens 
    * from the buffer so that thes tokens may some how be 
    * used to determine the semantics. This method is invoked 
    * after the <code>init</code> method is invoked.    
    */
   protected abstract void parse();
}
