package com.payexpress.electric.app.utils;

public interface IRfidParam {

	public static byte PSAM_NUM_1 = 0x01;
	public static byte PSAM_NUM_2 = 0x02;
	public static byte PSAM_NUM_3 = 0x03;
	public static byte PSAM_NUM_4 = 0x04;
	public static byte[] PSAM_NUM = {PSAM_NUM_1,PSAM_NUM_2,PSAM_NUM_3,PSAM_NUM_4};
	public static String[] PSAM_NUM_STR = {"PSAM_1","PSAM_2","PSAM_3","PSAM_4"};
	
	public static byte PSAM_MODE_38400 = (byte)0x38;
	public static byte PSAM_MODE_9600 = (byte)0x96;
	public static byte[] PSAM_MODE = {PSAM_MODE_38400,PSAM_MODE_9600
		//,PSAM_MODE_55800
		};
	public static String[] PSAM_MODE_STR = {"38400","9600"
		//,"55800"
		};
	public static byte[] dcm_f1 = new byte[]{0x04,(byte) 0xd6,(byte) 0x81,0x00,0x31};
	public static byte[] dcm_f2 = new byte[]{0x04,(byte) 0xd6,(byte) 0x82,0x00,0x0c};
	public static byte[] dcm_f3 = new byte[]{0x04,(byte) 0xd6,(byte) 0x83,0x00,(byte) 0x84};
	public static byte[] dcm_f4 = new byte[]{0x04,(byte) 0xd6,(byte) 0x83,(byte) 0x80,(byte) 0x86};
	public static byte[] dcm_f5 = new byte[]{0x04,(byte) 0xd6,(byte) 0x84,0x00,(byte) 0x84};
	public static byte[] dcm_f6 = new byte[]{0x04,(byte) 0xd6,(byte) 0x84,(byte) 0x80,(byte) 0x86};
	public static byte[] dcm_f7 = new byte[]{0x04,(byte) 0xd6,(byte) 0x85,0x00,0x35};
	
	public static byte SUC[] = {0x00};
	public static byte ERR[] = {0x01};
	
	//����û�
	public static byte[] NewUser = new byte[]{0x00,0x02};
	public static byte[] OldUser = new byte[]{0x01,0x02};
	
	//������Կ
	public static byte SetKey1ERR = 0x04;
	public static byte SetKey2ERR = 0x05;
	public static byte SetKey3ERR = 0x06;
	public static byte SetKey4ERR = 0x07;
	
	//д�ļ�
	public static byte WriteFile1ERR = 0x08;
	public static byte WriteFile2ERR = 0x09;
	public static byte WriteFile3ERR = 0x10;
	public static byte WriteFile4ERR = 0x11;
	public static byte WriteFile5ERR = 0x12;
	public static byte WriteFile6ERR = 0x13;
	public static byte WriteFile7ERR = 0x14;
	
}
