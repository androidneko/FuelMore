package com.androidcat.utilities.tlv;

import com.androidcat.utilities.chars.CommonMethods;
import com.androidcat.utilities.log.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TLVUtil {
	/// <summary>
    /// 构造TLV
    /// </summary>
    /// <param name="buffer">
	///daijianke@abchina.com
    public static List<TLVEntity> Construct(byte[] buffer)
    {
        List<TLVEntity> list = new ArrayList<TLVEntity>();
        int currentTLVIndex = 0;
        int currentIndex = 0;
        int currentStatus = 'T'; //状态字符
        int valueSize = 0;

        TLVEntity tlvEntity = null;

        while (currentIndex < buffer.length)
        {
            switch (currentStatus)
            {
                case 'T':
                    tlvEntity = new TLVEntity();
                    valueSize = 0;
                    //判断是否单一结构
                    if ((buffer[currentIndex] & 0x20) != 0x20)
                    {
                        tlvEntity.Sub_TLVEntity = null; //单一结构时将子Tag置空【】
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);

                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }
                    }
                    else
                    {
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);

                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }

                        //分析SubTag
                        int subLength = 0;

                        byte[] temp;
                        if ((buffer[currentIndex] & 0x80) == 0x80)
                        {
                        	int lengthSize = buffer[currentIndex] & 0x7f;
                            /*for (int index = 0; index < 2; index++)
                            {
                                subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                            }*/
                        	byte[] lenBytes = new byte[lengthSize];
                            for(int k =0; k < lengthSize;k++){
                            	lenBytes[k] = buffer[currentIndex + 1 + k];
                            }
                        	subLength = CommonMethods.bytesToShort(lenBytes);

                            temp = new byte[subLength];

                            System.arraycopy(buffer, currentIndex + 1+lengthSize, temp, 0, subLength);
                        }
                        else
                        {
                            subLength = buffer[currentIndex];
                            temp = new byte[subLength];
                            System.arraycopy(buffer, currentIndex + 1, temp, 0, subLength);
                        }

                        int oLength;
                        tlvEntity.Sub_TLVEntity = new TLVEntity();
                        List<TLVEntity> tempList = Construct(temp);
                        tlvEntity.Sub_TLVEntity = tempList.get(0);
                    }
                    currentStatus = 'L';
                    break;
                case 'L':
                    //判断长度字节的最高位是否为1，如果为1，则该字节为长度扩展字节，由下一个字节开始决定长度
                    if ((buffer[currentIndex] & 0x80) != 0x80)
                    {
                        tlvEntity.Length = new byte[1];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, 1);

                        tlvEntity.LengthSize = 1;

                        valueSize = tlvEntity.Length[0];
                        currentIndex += 1;
                    }
                    else
                    {
                        //为1的情况
                    	int lengthSize = buffer[currentIndex] & 0x7f;
                        //从下一个字节开始算Length域
                          currentIndex += 1; 
                          /*for (int index = 0; index < 2; index++)
                          {
                              subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                          }*/
                      	byte[] lenBytes = new byte[lengthSize];
                          for(int k =0; k < lengthSize;k++){
                          	lenBytes[k] = buffer[currentIndex + k];
                          }
                          valueSize = CommonMethods.bytesToShort(lenBytes);

                        tlvEntity.Length = new byte[lengthSize];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, lengthSize);

                        tlvEntity.LengthSize = lengthSize;

                        currentIndex += lengthSize;
                    }

                    currentStatus = 'V';
                    break;
                case 'V':
                    tlvEntity.Value = new byte[valueSize];
                    System.arraycopy(buffer, currentIndex, tlvEntity.Value, 0, valueSize);

                    currentIndex += valueSize;

                    //进入下一个TLV构造循环
                    list.add(tlvEntity);

                    currentStatus = 'T';
                    break;
                default:
                    return new ArrayList<TLVEntity>();
            }
        }

        return list;
    }
    
    public static List<TLVEntity> ConstructTL(byte[] buffer)
    {
        List<TLVEntity> list = new ArrayList<TLVEntity>();
        int currentTLVIndex = 0;
        int currentIndex = 0;
        int currentStatus = 'T'; //状态字符
        int valueSize = 0;

        TLVEntity tlvEntity = null;

        while (currentIndex <= buffer.length)
        {
            switch (currentStatus)
            {
                case 'T':
                    tlvEntity = new TLVEntity();
                    valueSize = 0;
                    //判断是否单一结构
                    if ((buffer[currentIndex] & 0x20) != 0x20)
                    {
                        tlvEntity.Sub_TLVEntity = null; //单一结构时将子Tag置空【】
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);
                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }
                    }
                    else
                    {
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);

                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }

                        //分析SubTag
                        int subLength = 0;

                        byte[] temp;
                        if ((buffer[currentIndex] & 0x80) == 0x80)
                        {
                        	int lengthSize = buffer[currentIndex] & 0x7f;
                            /*for (int index = 0; index < 2; index++)
                            {
                                subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                            }*/
                        	byte[] lenBytes = new byte[lengthSize];
                            for(int k =0; k < lengthSize;k++){
                            	lenBytes[k] = buffer[currentIndex + 1 + k];
                            }
                        	subLength = CommonMethods.bytesToShort(lenBytes);

                            temp = new byte[subLength];
                            System.arraycopy(buffer, currentIndex + 1 + lengthSize, temp, 0, subLength);
                        }
                        else
                        {
                            subLength = buffer[currentIndex];

                            temp = new byte[subLength];
                            System.arraycopy(buffer, currentIndex + 1, temp, 0, subLength);
                        }
                        
                        int oLength;
                        tlvEntity.Sub_TLVEntity = new TLVEntity();
                        List<TLVEntity> tempList = ConstructTL(temp);
                        tlvEntity.Sub_TLVEntity = tempList.get(0);
                    }

                    currentStatus = 'L';
                    break;
                case 'L':
                    //判断长度字节的最高位是否为1，如果为1，则该字节为长度扩展字节，由下一个字节开始决定长度
                    if ((buffer[currentIndex] & 0x80) != 0x80)
                    {
                        tlvEntity.Length = new byte[1];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, 1);

                        tlvEntity.LengthSize = 1;
                        valueSize = tlvEntity.Length[0];
                        currentIndex += 1;
                    }
                    else
                    {
                        //为1的情况
                    	int lengthSize = buffer[currentIndex] & 0x7f;
                        //从下一个字节开始算Length域
                          currentIndex += 1; 
                          /*for (int index = 0; index < 2; index++)
                          {
                              subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                          }*/
                      	byte[] lenBytes = new byte[lengthSize];
                          for(int k =0; k < lengthSize;k++){
                          	lenBytes[k] = buffer[currentIndex + k];
                          }
                          valueSize = CommonMethods.bytesToShort(lenBytes);

                        tlvEntity.Length = new byte[lengthSize];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, lengthSize);

                        tlvEntity.LengthSize = lengthSize;

                        currentIndex += lengthSize;
                    }

                    currentStatus = 'V';
                    break;
                case 'V':
                    tlvEntity.Value = new byte[valueSize];
                    //System.arraycopy(buffer, currentIndex, tlvEntity.Value, 0, valueSize);

                    //currentIndex += valueSize;

                    //进入下一个TLV构造循环
                    Logger.d(tlvEntity.toString());
                    list.add(tlvEntity);
                    if (currentIndex == buffer.length){
                    	return list;
                    }
                    currentStatus = 'T';
                    break;
                default:
                    return new ArrayList<TLVEntity>();
            }
        }

        return list;
    }
    
    public static Map<String,TLVEntity> ConstructTLMap(byte[] buffer)
    {
        //List<TLVEntity> list = new ArrayList<TLVEntity>();
        Map<String,TLVEntity> map = new LinkedHashMap<String,TLVEntity>();
        int currentTLVIndex = 0;
        int currentIndex = 0;
        int currentStatus = 'T'; //状态字符
        int valueSize = 0;

        TLVEntity tlvEntity = null;

        while (currentIndex <= buffer.length)
        {
            switch (currentStatus)
            {
                case 'T':
                    tlvEntity = new TLVEntity();
                    valueSize = 0;
                    //判断是否单一结构
                    if ((buffer[currentIndex] & 0x20) != 0x20)
                    {
                        tlvEntity.Sub_TLVEntity = null; //单一结构时将子Tag置空【】
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);
                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }
                    }
                    else
                    {
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);

                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }

                        //分析SubTag
                        int subLength = 0;

                        byte[] temp;
                        if ((buffer[currentIndex] & 0x80) == 0x80)
                        {
                        	int lengthSize = buffer[currentIndex] & 0x7f;
                            /*for (int index = 0; index < 2; index++)
                            {
                                subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                            }*/
                        	byte[] lenBytes = new byte[lengthSize];
                            for(int k =0; k < lengthSize;k++){
                            	lenBytes[k] = buffer[currentIndex + 1 + k];
                            }
                        	subLength = CommonMethods.bytesToShort(lenBytes);
                            temp = new byte[subLength];
                            
                            System.arraycopy(buffer, currentIndex + 1 + lengthSize, temp, 0, subLength);
                        }
                        else
                        {
                            subLength = buffer[currentIndex];

                            temp = new byte[subLength];
                            System.arraycopy(buffer, currentIndex + 1, temp, 0, subLength);
                        }
                        
                        int oLength;
                        tlvEntity.Sub_TLVEntity = new TLVEntity();
                        List<TLVEntity> tempList = ConstructTL(temp);
                        tlvEntity.Sub_TLVEntity = tempList.get(0);
                    }

                    currentStatus = 'L';
                    break;
                case 'L':
                    //判断长度字节的最高位是否为1，如果为1，则该字节为长度扩展字节，由下一个字节开始决定长度
                    if ((buffer[currentIndex] & 0x80) != 0x80)
                    {
                        tlvEntity.Length = new byte[1];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, 1);

                        tlvEntity.LengthSize = 1;
                        valueSize = tlvEntity.Length[0];
                        currentIndex += 1;
                    }
                    else
                    {
                        //为1的情况
                    	int lengthSize = buffer[currentIndex] & 0x7f;
                        //从下一个字节开始算Length域
                          currentIndex += 1; 
                          /*for (int index = 0; index < 2; index++)
                          {
                              subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                          }*/
                      	byte[] lenBytes = new byte[lengthSize];
                          for(int k =0; k < lengthSize;k++){
                          	lenBytes[k] = buffer[currentIndex + k];
                          }
                          valueSize = CommonMethods.bytesToShort(lenBytes);

                        tlvEntity.Length = new byte[lengthSize];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, lengthSize);

                        tlvEntity.LengthSize = lengthSize;

                        currentIndex += lengthSize;
                    }

                    currentStatus = 'V';
                    break;
                case 'V':
                    tlvEntity.Value = new byte[valueSize];
                    //System.arraycopy(buffer, currentIndex, tlvEntity.Value, 0, valueSize);

                    //currentIndex += valueSize;

                    //进入下一个TLV构造循环
                    Logger.d("TL:" + tlvEntity.toString());
                    map.put(tlvEntity.getTagName(), tlvEntity);
                    if (currentIndex == buffer.length){
                    	return map;
                    }
                    currentStatus = 'T';
                    break;
            }
        }

        return map;
    }

    /// <summary>
    /// 解析TLV
    /// </summary>
    /// <param name="list">
    /// <returns></returns>
    public static byte[] Parse(List<TLVEntity> list)
    {
        byte[] buffer = new byte[4096];
        int currentIndex = 0;
        int currentTLVIndex = 0;
        int valueSize = 0;

        while (currentTLVIndex < list.size())
        {
            valueSize = 0;
            TLVEntity entity = list.get(currentTLVIndex);

            System.arraycopy(entity.Tag, 0, buffer, currentIndex, entity.TagSize);    //解析Tag

            currentIndex += entity.TagSize;

            for (int index = 0; index < entity.LengthSize; index++)
            {
                valueSize += entity.Length[index] << (index * 8); //计算Length域的长度
            }
            if (valueSize > 127)
            {
                buffer[currentIndex] = (byte)(0x80 | entity.LengthSize);
                currentIndex += 1;
            }

            System.arraycopy(entity.Length, 0, buffer, currentIndex, entity.LengthSize);  //解析Length

            currentIndex += entity.LengthSize;
            //判断是否包含子嵌套TLV
            if (entity.Sub_TLVEntity == null)
            {
            	System.arraycopy(entity.Value, 0, buffer, currentIndex, valueSize);   //解析Value
                currentIndex += valueSize;
            }
            else
            {
            	List<TLVEntity> subTLVEntityList = new ArrayList<TLVEntity>();
            	subTLVEntityList.add(entity.Sub_TLVEntity);
                byte[] tempBuffer = Parse(subTLVEntityList);
                System.arraycopy(tempBuffer, 0, buffer, currentIndex, tempBuffer.length); //解析子嵌套TLV
                currentIndex += tempBuffer.length;
            }

            currentTLVIndex++;
        }

        byte[] resultBuffer = new byte[currentIndex];
        System.arraycopy(buffer, 0, resultBuffer, 0, currentIndex);

        return resultBuffer;
    }
    
  /// <summary>
    /// 构造TLV
    /// </summary>
    /// <param name="buffer">
    public static Map<String,TLVEntity> ConstructTLVMap(byte[] buffer)
    {
    	Map<String,TLVEntity> map = new LinkedHashMap<String,TLVEntity>();
        int currentTLVIndex = 0;
        int currentIndex = 0;
        int currentStatus = 'T'; //状态字符
        int valueSize = 0;

        TLVEntity tlvEntity = null;

        while (currentIndex < buffer.length)
        {
            switch (currentStatus)
            {
                case 'T':
                    tlvEntity = new TLVEntity();
                    valueSize = 0;
                    //判断是否单一结构
                    if ((buffer[currentIndex] & 0x20) != 0x20)
                    {
                        tlvEntity.Sub_TLVEntity = null; //单一结构时将子Tag置空【】
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);

                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }
                    }
                    else
                    {
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);

                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }

                        //分析SubTag
                        int subLength = 0;

                        byte[] temp;
                        if ((buffer[currentIndex] & 0x80) == 0x80)
                        {
                        	int lengthSize = buffer[currentIndex] & 0x7f;
                            /*for (int index = 0; index < 2; index++)
                            {
                                subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                            }*/
                        	byte[] lenBytes = new byte[lengthSize];
                            for(int k =0; k < lengthSize;k++){
                            	lenBytes[k] = buffer[currentIndex + 1 + k];
                            }
                        	subLength = CommonMethods.bytesToShort(lenBytes);
                            temp = new byte[subLength];
                            
                            System.arraycopy(buffer, currentIndex + 1 + lengthSize, temp, 0, subLength);
                        }
                        else
                        {
                            subLength = buffer[currentIndex];

                            temp = new byte[subLength];

                            System.arraycopy(buffer, currentIndex + 1, temp, 0, subLength);
                        }
                        
                        int oLength;
                        tlvEntity.Sub_TLVEntity = new TLVEntity();
                        List<TLVEntity> tempList = ConstructAndSave(temp,map);
                        for (TLVEntity tlv : tempList){
                            map.put(tlv.getTagName(),tlv);
                        }
                        tlvEntity.Sub_TLVEntity = tempList.get(0);
                    }

                    currentStatus = 'L';
                    break;
                case 'L':
                    //判断长度字节的最高位是否为1，如果为1，则该字节为长度扩展字节，由下一个字节开始决定长度
                    if ((buffer[currentIndex] & 0x80) != 0x80)
                    {
                        tlvEntity.Length = new byte[1];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, 1);

                        tlvEntity.LengthSize = 1;

                        valueSize = tlvEntity.Length[0];
                        currentIndex += 1;
                    }
                    else
                    {
                        //为1的情况

                        int lengthSize = buffer[currentIndex] & 0x7f;

                        currentIndex += 1; //从下一个字节开始算Length域

                        /*for (int index = 0; index < lengthSize; index++)
                        {
                            valueSize += buffer[currentIndex + index] << (index * 8); //计算Length域的长度
                        }*/

                        byte[] lenBytes = new byte[lengthSize];
                        for(int k =0; k < lengthSize;k++){
                        	lenBytes[k] = buffer[currentIndex + k];
                        }
                        valueSize = CommonMethods.bytesToShort(lenBytes);
                        
                        tlvEntity.Length = new byte[lengthSize];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, lengthSize);

                        tlvEntity.LengthSize = lengthSize;

                        currentIndex += lengthSize;
                    }

                    currentStatus = 'V';
                    break;
                case 'V':
                    tlvEntity.Value = new byte[valueSize];
                    System.arraycopy(buffer, currentIndex, tlvEntity.Value, 0, valueSize);

                    currentIndex += valueSize;

                    //进入下一个TLV构造循环
                    Logger.d(tlvEntity.toString());
                    map.put(tlvEntity.getTagName(),tlvEntity);

                    currentStatus = 'T';
                    break;
            }
        }
        return map;
    }
    
    public static List<TLVEntity> ConstructAndSave(byte[] buffer,Map<String, TLVEntity> map)
    {
        List<TLVEntity> list = new ArrayList<TLVEntity>();
        int currentTLVIndex = 0;
        int currentIndex = 0;
        int currentStatus = 'T'; //状态字符
        int valueSize = 0;

        TLVEntity tlvEntity = null;

        while (currentIndex < buffer.length)
        {
            switch (currentStatus)
            {
                case 'T':
                    tlvEntity = new TLVEntity();
                    valueSize = 0;
                    //判断是否单一结构
                    if ((buffer[currentIndex] & 0x20) != 0x20)
                    {
                        tlvEntity.Sub_TLVEntity = null; //单一结构时将子Tag置空【】
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);

                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }
                    }
                    else
                    {
                        //判断是否多字节Tag
                        if ((buffer[currentIndex] & 0x1f) == 0x1f)
                        {
                            int endTagIndex = currentIndex;
                            while ((buffer[++endTagIndex] & 0x80) == 0x80) ; //判断第二个字节的最高位是否为1
                            int tagSize = endTagIndex - currentIndex + 1; //计算Tag包含多少字节

                            tlvEntity.Tag = new byte[tagSize];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, tagSize);

                            tlvEntity.TagSize = tagSize;

                            currentIndex += tagSize;
                        }
                        else
                        {
                            tlvEntity.Tag = new byte[1];
                            System.arraycopy(buffer, currentIndex, tlvEntity.Tag, 0, 1);

                            tlvEntity.TagSize = 1;

                            currentIndex += 1;
                        }

                        //分析SubTag
                        int subLength = 0;

                        byte[] temp;
                        if ((buffer[currentIndex] & 0x80) == 0x80)
                        {
                        	int lengthSize = buffer[currentIndex] & 0x7f;
                            /*for (int index = 0; index < 2; index++)
                            {
                                subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                            }*/
                        	byte[] lenBytes = new byte[lengthSize];
                            for(int k =0; k < lengthSize;k++){
                            	lenBytes[k] = buffer[currentIndex + 1 + k];
                            }
                        	subLength = CommonMethods.bytesToShort(lenBytes);

                            temp = new byte[subLength];

                            System.arraycopy(buffer, currentIndex + 1 + lengthSize, temp, 0, subLength);
                        }
                        else
                        {
                            subLength = buffer[currentIndex];

                            temp = new byte[subLength];

                            System.arraycopy(buffer, currentIndex + 1, temp, 0, subLength);
                        }

                        int oLength;
                        tlvEntity.Sub_TLVEntity = new TLVEntity();
                        List<TLVEntity> tempList = ConstructAndSave(temp,map);
                        if (tempList != null && tempList.size() > 0){
                        	 for (TLVEntity tlv : tempList){
                             	map.put(tlv.getTagName(), tlv);
                             }
                             tlvEntity.Sub_TLVEntity = tempList.get(0);
                        }
                       
                    }

                    currentStatus = 'L';
                    break;
                case 'L':
                    //判断长度字节的最高位是否为1，如果为1，则该字节为长度扩展字节，由下一个字节开始决定长度
                    if ((buffer[currentIndex] & 0x80) != 0x80)
                    {
                        tlvEntity.Length = new byte[1];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, 1);

                        tlvEntity.LengthSize = 1;

                        valueSize = tlvEntity.Length[0];
                        currentIndex += 1;
                    }
                    else
                    {
                        //为1的情况
                        int lengthSize = buffer[currentIndex] & 0x7f;
                      //从下一个字节开始算Length域
                        currentIndex += 1; 
                        /*for (int index = 0; index < 2; index++)
                        {
                            subLength += buffer[currentIndex + 1 + index] << (index * 8); //计算Length域的长度
                        }*/
                    	byte[] lenBytes = new byte[lengthSize];
                        for(int k =0; k < lengthSize;k++){
                        	lenBytes[k] = buffer[currentIndex + k];
                        }
                        valueSize = CommonMethods.bytesToShort(lenBytes);

                        tlvEntity.Length = new byte[lengthSize];
                        System.arraycopy(buffer, currentIndex, tlvEntity.Length, 0, lengthSize);

                        tlvEntity.LengthSize = lengthSize;

                        currentIndex += lengthSize;
                    }

                    currentStatus = 'V';
                    break;
                case 'V':
                    tlvEntity.Value = new byte[valueSize];
                    System.arraycopy(buffer, currentIndex, tlvEntity.Value, 0, valueSize);

                    currentIndex += valueSize;

                    //进入下一个TLV构造循环
                    Logger.d(tlvEntity.toString());
                    list.add(tlvEntity);

                    currentStatus = 'T';
                    break;
                default:
                    return new ArrayList<TLVEntity>();
            }
        }

        return list;
    }
}
