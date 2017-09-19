package com.androidcat.utilities.tlv;

import com.androidcat.utilities.chars.CommonMethods;

import java.util.Locale;


public class TLVEntity {
        /// <summary>
        /// 标记
        /// </summary>
        public byte[] Tag ;
 
        /// <summary>
        /// 数据长度
        /// </summary>
        public byte[] Length;
 
        /// <summary>
        /// 数据
        /// </summary>
        public byte[] Value ;
 
        /// <summary>
        /// 标记占用字节数
        /// </summary>
        public int TagSize ;
 
        /// <summary>
        /// 数据长度占用字节数
        /// </summary>
        public int LengthSize ;
 
        /// <summary>
        /// 子嵌套TLV实体
        /// </summary>
        public TLVEntity Sub_TLVEntity ;

		public byte[] getTag() {
			return Tag;
		}

		public void setTag(byte[] tag) {
			Tag = tag;
		}

		public byte[] getLength() {
			return Length;
		}

		public void setLength(byte[] length) {
			Length = length;
		}

		public byte[] getValue() {
			return Value;
		}

		public void setValue(byte[] value) {
			Value = value;
		}

		public int getTagSize() {
			return TagSize;
		}

		public void setTagSize(int tagSize) {
			TagSize = tagSize;
		}

		public int getLengthSize() {
			return LengthSize;
		}

		public void setLengthSize(int lengthSize) {
			LengthSize = lengthSize;
		}

		public TLVEntity getSub_TLVEntity() {
			return Sub_TLVEntity;
		}

		public void setSub_TLVEntity(TLVEntity sub_TLVEntity) {
			Sub_TLVEntity = sub_TLVEntity;
		}

		public String getTagName(){
			return CommonMethods.bytesToHexString(Tag).toUpperCase(Locale.ENGLISH);
		}
		
		public String getHexValue(){
			return CommonMethods.bytesToHexString(Value).toUpperCase(Locale.ENGLISH);
		}
		
		public int getValueLen(){
			return CommonMethods.bytesToShort(Length);
		}
		
		@Override
		public String toString() {
			return "------tag:" + CommonMethods.bytesToHexString(Tag) + " " +
					"TagSize:" + TagSize + " " +
					"Length:" + CommonMethods.bytesToHexString(Length) + " " +
					"Value:" + CommonMethods.bytesToHexString(Value) + " " +
					"LengthSize:" + CommonMethods.bytesToHexString(Value).length() ;
		}
        
}
