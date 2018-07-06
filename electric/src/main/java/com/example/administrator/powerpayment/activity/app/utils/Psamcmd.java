package com.example.administrator.powerpayment.activity.app.utils;

import android.util.Log;

import com.rd.io.EMgpio;
import com.halio.Rfid;

public class Psamcmd {
    private static byte bSamNum;
    private static byte bSamBaud;
    private static byte[] bResponse = new byte[256];
    private static int[] bResponseLen = new int[1];
    private static byte[] bCmd = null;

    public static byte[] reset(byte SamNum, byte SamBaud) {

        Rfid.closeCommPort();
        Rfid.powerOff();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Rfid.powerOn();
        Rfid.openCommPort();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        byte[] SN = new byte[8];
        Boolean ret = Rfid.samReset(SamNum, SamBaud, bResponse, bResponseLen);
        if (ret) {
            int i = 0;
            for (i = 0; i < SN.length; i++)
                SN[i] = bResponse[i + bResponseLen[0] - 8];
        } else {
            return IRfidParam.ERR;
        }

        return SN;
    }

    //复位GPIO口
    public static void ResetGPIO(int gpio_index) {
        EMgpio.GPIOInit();
        EMgpio.setGpioMode(gpio_index, 0);
        EMgpio.SetGpioOutput(gpio_index);
        EMgpio.SetGpioDataLow(gpio_index);
    }

    /*
     *getversion() 获取设备版本
	 *当flags == 1时，插入卡片；当flags == 0时未插入卡片
	 */
    public static int getversion() {
        byte[] hwVersion = new byte[13];
        int flags = 0;

        int getv = Rfid.getHwVersion(hwVersion);
        Log.e("mian", "获取的数据：" + hxtoString(hwVersion));
        if ((new String(hwVersion)).equals("V0.1A_psamin1"))
            flags = 1;
        else if ((new String(hwVersion)).equals("V0.1A_psamin0"))
            flags = 0;
        else//数据错乱重启
            flags = 2;
        return flags;
    }

    //设置左喇叭声音
    public static void SetLeftSPK() {
        EMgpio.SetGpioDataHigh(55);
    }

    //设置闪光灯
    public static Boolean SetLed(int mode) {
        Boolean set_led = false;
        //mode为1灯亮,0灯灭
        if (mode == 1) {
            set_led = EMgpio.SetGpioDataHigh(94);
        } else if (mode == 0)
            set_led = EMgpio.SetGpioDataLow(94);
        else
            set_led = false;

        return set_led;
    }

    public static byte[] readCardstate(byte SamNum) {
        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x92, 0x00, 0x01};
        Boolean r_s = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if (r_s & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {
            if (bResponse[0] == 0x00)
                return IRfidParam.NewUser;
            else
                return IRfidParam.OldUser;
        } else
            return IRfidParam.ERR;
    }

    public static byte[] readCardInfo(byte SamNum) {
        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x9f, 0x05, (byte) 0x80};
        Boolean r_f = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);

        byte[] Info = new byte[128];
        int i = 0;

        if (r_f & (bResponse[bResponseLen[0] - 2] & 0xff) == 0x90) {
            for (i = 0; i < Info.length; i++)
                Info[i] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }
        return Info;
    }

    public static Boolean selectW(byte SamNum) {
        bCmd = new byte[]{0x00, (byte) 0xa4, 0x00, 0x00, 0x02, (byte) 0xdf, 0x01};
        Boolean s_w = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);

        if (!s_w)
            return false;
        if (!(((bResponse[bResponseLen[0] - 2] & 0xff) != 0x90) | (bResponse[bResponseLen[0] - 2] & 0xff) != 0x61))
            return false;
        return true;
    }

    public static byte[] getRand(byte SamNum) {
        bCmd = new byte[]{0x00, (byte) 0x84, 0x00, 0x00, 0x08};
        Boolean r_GR = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        int i = 0;
        byte data[] = new byte[8];
        if (r_GR & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {
            for (i = 0; i < data.length; i++)
                data[i] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }
        return data;
    }

    private static void cleanbcmd() {
        int i = 0;
        for (i = 0; i < bResponse.length; i++)
            bResponse[i] = 0x00;
    }

    public static Boolean UserProving(byte SamNum, byte[] Rand, byte[] Des1) {
        byte[] bCmd1 = new byte[]{0x00, (byte) 0x88, 0x00, 0x01, 0x08};
        cleanbcmd();
        int i = 0;
        bCmd = PlousByte(bCmd1, Rand);
        Boolean r_U = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if ((!r_U) & ((bResponse[bResponseLen[0] - 2] & 0xff) != 0x90))
            return false;

        byte[] RandEnc = new byte[8];
        for (i = 0; i < RandEnc.length; i++)
            RandEnc[i] = bResponse[i];
        Boolean result = equals(RandEnc, Des1);
        return result;
    }

    public static String Hxtostring(byte data[]) {
        int k = 0;
        String da;
        StringBuffer sbf2 = new StringBuffer();
        for (k = 0; k < data.length; k++) {
            da = Integer.toHexString(data[k] & 0xff);
            sbf2.append(da.length() == 1 ? "0" + da : da);
        }
        return sbf2.toString();
    }

    private static String hxtoString(byte[] data) {
        int k = 0;
        String da;
        StringBuffer sbf2 = new StringBuffer();
        for (k = 0; k < data.length; k++) {
            da = Integer.toHexString(data[k] & 0xff);
            sbf2.append(da.length() == 1 ? "0" + da : da);
        }
        return sbf2.toString();
    }

    public static Boolean PowerProving(byte SamNum, byte[] Des2) {
        byte[] bcmd = new byte[]{0x00, (byte) 0x82, 0x00, 0x03, 0x08};
        bCmd = PlousByte(bcmd, Des2);

        Boolean r_p = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if ((!r_p) & ((bResponse[bResponseLen[0] - 2] & 0xff) != 0x90))
            return false;
        return true;
    }

    public static Boolean check_d(byte SamNum) {
        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x85, 0x00, 0x31};
        Boolean r_c = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if (!r_c)
            return false;
        return true;
    }

    public static String getUser(byte[] file1) {
        byte[] data = new byte[5];
        int i = 0;
        for (i = 0; i < data.length; i++)
            data[i] = file1[i + 37];

        return hxtoString(data);
    }

    public static Boolean BuyPowerProving(byte SamNum, byte[] Des3) {
        byte[] bcmd = new byte[]{0x00, (byte) 0x82, 0x00, 0x04, 0x08};
        bCmd = PlousByte(bcmd, Des3);

        Boolean r_p = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if ((!r_p) & ((bResponse[bResponseLen[0] - 2] & 0xff) != 0x90))
            return false;
        return true;
    }

    public static byte SetKey(byte SamNum, byte[] keyout1, byte[] keyout2, byte[] keyout3, byte[] keyout4) {
        byte[] bcmd1 = new byte[]{(byte) 0x84, (byte) 0xd4, 0x01, (byte) 0xff, 0x20};
        bCmd = PlousByte(bCmd, keyout1);
        Boolean S_k1 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);

        if (S_k1 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {
            bCmd = PlousByte(bcmd1, keyout2);
            Boolean S_k2 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
            if (S_k2 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                bCmd = PlousByte(bcmd1, keyout3);
                Boolean S_k3 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
                if (S_k3 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                    bCmd = PlousByte(bcmd1, keyout4);
                    Boolean S_k4 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
                    if (S_k4 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                        return IRfidParam.SUC[0];
                    } else
                        return IRfidParam.SetKey4ERR;
                } else
                    return IRfidParam.SetKey3ERR;
            } else
                return IRfidParam.SetKey2ERR;
        } else
            return IRfidParam.SetKey1ERR;
    }

    public static byte WriteCare(byte SamNum, byte[] file1, byte[] file2, byte[] file3, byte[] file4, byte[] file5, byte[] file6, byte[] file7) {
        bCmd = PlousByte(IRfidParam.dcm_f5, file5);

        Boolean W_C5 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if (W_C5 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

            bCmd = PlousByte(IRfidParam.dcm_f2, file2);
            Boolean W_C2 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
            if (W_C2 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                bCmd = PlousByte(IRfidParam.dcm_f3, file3);
                Boolean W_C3 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
                if (W_C3 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                    bCmd = PlousByte(IRfidParam.dcm_f4, file4);
                    Boolean W_C4 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
                    if (W_C4 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                        bCmd = PlousByte(IRfidParam.dcm_f1, file1);
                        Boolean W_C1 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
                        if (W_C1 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                            bCmd = PlousByte(IRfidParam.dcm_f6, file6);
                            Boolean W_C6 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
                            if (W_C6 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                                bCmd = PlousByte(IRfidParam.dcm_f7, file7);
                                Boolean W_C7 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
                                if (W_C7 & ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90)) {

                                    return IRfidParam.SUC[0];
                                } else
                                    return IRfidParam.WriteFile7ERR;
                            } else
                                return IRfidParam.WriteFile6ERR;
                        } else
                            return IRfidParam.WriteFile1ERR;
                    } else
                        return IRfidParam.WriteFile4ERR;
                } else
                    return IRfidParam.WriteFile3ERR;
            } else
                return IRfidParam.WriteFile2ERR;
        } else
            return IRfidParam.WriteFile5ERR;
    }

    //ƴ���ֽ�����
    private static byte[] PlousByte(byte[] bcm, byte[] rand) {
        int i = 0;
        byte[] data = new byte[bcm.length + rand.length];
        for (i = 0; i < bcm.length; i++)
            data[i] = bcm[i];

        for (i = 0; i < rand.length; i++)
            data[i + bcm.length] = rand[i];

        return data;
    }

    private static boolean equals(byte[] data1, byte[] data2) {
        if (data1 == null && data2 == null) {
            return true;
        }
        if (data1 == null || data2 == null) {
            return false;
        }
        if (data1.length != data2.length) {
            return false;
        }
        return memcmp(data1, data2);
    }

    private static Boolean memcmp(byte data1[], byte data2[]) {
        int i = 0;
        Boolean bEquals = true;
        for (i = 0; i < data1.length && i < data2.length; i++) {
            if (data1[i] != data2[i]) {
                bEquals = false;
                break;
            } else
                bEquals = true;
        }
        return bEquals;
    }

    public static byte[] readDataInfo(byte SamNum) {
        int i = 0;
        byte datainfo[] = new byte[45];
        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x81, 0x00, 0x2d};
        Boolean r_di = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);

        if (r_di & (((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90) | ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x6B))) {
            for (i = 0; i < datainfo.length; i++)
                datainfo[i] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }
        return datainfo;
    }

    public static byte[] readWallet(byte SamNum) {
        int i = 0;
        byte Wallet[] = new byte[8];
        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x82, 0x00, 0x08};
        Boolean r_di = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);

        if (r_di & (((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90) | ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x6B))) {
            for (i = 0; i < Wallet.length; i++)
                Wallet[i] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }
        return Wallet;
    }

    public static byte[] readCAF(byte SamNum) {
        int i = 0;
        byte CAF[] = new byte[258];
        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x83, 0x00, (byte) 0x81};

        Boolean r_cf1 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if (r_cf1 & (((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90) | ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x6B))) {
            for (i = 0; i < 129; i++)
                CAF[i] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }

        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x83, (byte) 0x81, (byte) 0x81};
        Boolean r_cf2 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);

        if (r_cf2 & (((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90) | ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x6B))) {
            for (i = 0; i < 129; i++)
                CAF[i + 129] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }
        return CAF;

    }

    public static byte[] readSAF(byte SamNum) {
        int i = 0;
        byte SAF[] = new byte[258];
        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x84, 0x00, (byte) 0x81};
        Boolean r_sf1 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if (r_sf1 & (((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90) | ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x6B))) {
            for (i = 0; i < 129; i++)
                SAF[i] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }

        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x84, (byte) 0x81, (byte) 0x81};
        Boolean r_sf2 = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);
        if (r_sf2 & (((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90) | ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x6B))) {
            for (i = 0; i < 129; i++)
                SAF[i + 129] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }
        return SAF;
    }

    public static byte[] readWB(byte SamNum) {
        int i = 0;
        byte WB[] = new byte[49];
        bCmd = new byte[]{0x00, (byte) 0xb0, (byte) 0x85, 0x00, 0x31};
        Boolean r_di = Rfid.samCos(SamNum, bCmd, bCmd.length, bResponse, bResponseLen);

        if (r_di & (((bResponse[bResponseLen[0] - 2] & 0xff) == 0x90) | ((bResponse[bResponseLen[0] - 2] & 0xff) == 0x6B))) {
            for (i = 0; i < WB.length; i++)
                WB[i] = bResponse[i];
        } else {
            return IRfidParam.ERR;
        }
        return WB;
    }
}
