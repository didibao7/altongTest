package com.altong.web.logic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
import org.apache.commons.codec.binary.Base64;

import jasp.buildin.*;
import jasp.util.*;
import jasp.vbs.*;

public class EncLibrary {
	static vbarray m_lOnBits;
    static vbarray m_l2Power;
    static vbarray m_bytOnBits;
    static vbarray m_byt2Power;
    static vbarray m_InCo;
    static vbarray m_fbsub;
    static vbarray m_rbsub;
    static vbarray m_ptab;
    static vbarray m_ltab;
    static vbarray m_ftable;
    static vbarray m_rtable;
    static vbarray m_rco;
    static int m_Nk;
    static int m_Nb;
    static int m_Nr;
    static vbarray m_fi;
    static vbarray m_ri;
    static vbarray m_fkey;
    static vbarray m_rkey;
    static vbarray bytDest;
    static vbarray bytOut;
    static vbarray bytTemp;
    static vbarray buff;
    static vbarray bytMessageDe;
    
    public EncLibrary() throws Exception {
    	m_lOnBits = new vbarray();
        m_l2Power = new vbarray();
        m_bytOnBits = new vbarray();
        m_byt2Power = new vbarray();
        m_InCo = new vbarray();
        m_fbsub = new vbarray();
        m_rbsub = new vbarray();
        m_ptab = new vbarray();
        m_ltab = new vbarray();
        m_ftable = new vbarray();
        m_rtable = new vbarray();
        m_rco = new vbarray();
        m_Nk = 0;
        m_Nb = 0;
        m_Nr = 0;
        m_fi = new vbarray();
        m_ri = new vbarray();
        m_fkey = new vbarray();
        m_rkey = new vbarray();
        
        m_lOnBits = new vbarray(30);
        m_l2Power = new vbarray(30);
        m_bytOnBits = new vbarray(7);
        m_byt2Power = new vbarray(7);
        m_InCo = new vbarray(3);
        m_fbsub = new vbarray(255);
        m_rbsub = new vbarray(255);
        m_ptab = new vbarray(255);
        m_ltab = new vbarray(255);
        m_ftable = new vbarray(255);
        m_rtable = new vbarray(255);
        m_rco = new vbarray(29);
        m_fi = new vbarray(23);
        m_ri = new vbarray(23);
        m_fkey = new vbarray(119);
        m_rkey = new vbarray(119);
        m_InCo.setItem(0 ,11);
        m_InCo.setItem(1 ,13);
        m_InCo.setItem(2 ,9);
        m_InCo.setItem(3 ,14);
        m_bytOnBits.setItem(0 ,1);
        m_bytOnBits.setItem(1 ,3);
        m_bytOnBits.setItem(2 ,7);
        m_bytOnBits.setItem(3 ,15);
        m_bytOnBits.setItem(4 ,31);
        m_bytOnBits.setItem(5 ,63);
        m_bytOnBits.setItem(6 ,127);
        m_bytOnBits.setItem(7 ,255);
        m_byt2Power.setItem(0 ,1);
        m_byt2Power.setItem(1 ,2);
        m_byt2Power.setItem(2 ,4);
        m_byt2Power.setItem(3 ,8);
        m_byt2Power.setItem(4 ,16);
        m_byt2Power.setItem(5 ,32);
        m_byt2Power.setItem(6 ,64);
        m_byt2Power.setItem(7 ,128);
        m_lOnBits.setItem(0 ,1);
        m_lOnBits.setItem(1 ,3);
        m_lOnBits.setItem(2 ,7);
        m_lOnBits.setItem(3 ,15);
        m_lOnBits.setItem(4 ,31);
        m_lOnBits.setItem(5 ,63);
        m_lOnBits.setItem(6 ,127);
        m_lOnBits.setItem(7 ,255);
        m_lOnBits.setItem(8 ,511);
        m_lOnBits.setItem(9 ,1023);
        m_lOnBits.setItem(10 ,2047);
        m_lOnBits.setItem(11 ,4095);
        m_lOnBits.setItem(12 ,8191);
        m_lOnBits.setItem(13 ,16383);
        m_lOnBits.setItem(14 ,32767);
        m_lOnBits.setItem(15 ,65535);
        m_lOnBits.setItem(16 ,131071);
        m_lOnBits.setItem(17 ,262143);
        m_lOnBits.setItem(18 ,524287);
        m_lOnBits.setItem(19 ,1048575);
        m_lOnBits.setItem(20 ,2097151);
        m_lOnBits.setItem(21 ,4194303);
        m_lOnBits.setItem(22 ,8388607);
        m_lOnBits.setItem(23 ,16777215);
        m_lOnBits.setItem(24 ,33554431);
        m_lOnBits.setItem(25 ,67108863);
        m_lOnBits.setItem(26 ,134217727);
        m_lOnBits.setItem(27 ,268435455);
        m_lOnBits.setItem(28 ,536870911);
        m_lOnBits.setItem(29 ,1073741823);
        m_lOnBits.setItem(30 ,2147483647);
        m_l2Power.setItem(0 ,1);
        m_l2Power.setItem(1 ,2);
        m_l2Power.setItem(2 ,4);
        m_l2Power.setItem(3 ,8);
        m_l2Power.setItem(4 ,16);
        m_l2Power.setItem(5 ,32);
        m_l2Power.setItem(6 ,64);
        m_l2Power.setItem(7 ,128);
        m_l2Power.setItem(8 ,256);
        m_l2Power.setItem(9 ,512);
        m_l2Power.setItem(10 ,1024);
        m_l2Power.setItem(11 ,2048);
        m_l2Power.setItem(12 ,4096);
        m_l2Power.setItem(13 ,8192);
        m_l2Power.setItem(14 ,16384);
        m_l2Power.setItem(15 ,32768);
        m_l2Power.setItem(16 ,65536);
        m_l2Power.setItem(17 ,131072);
        m_l2Power.setItem(18 ,262144);
        m_l2Power.setItem(19 ,524288);
        m_l2Power.setItem(20 ,1048576);
        m_l2Power.setItem(21 ,2097152);
        m_l2Power.setItem(22 ,4194304);
        m_l2Power.setItem(23 ,8388608);
        m_l2Power.setItem(24 ,16777216);
        m_l2Power.setItem(25 ,33554432);
        m_l2Power.setItem(26 ,67108864);
        m_l2Power.setItem(27 ,134217728);
        m_l2Power.setItem(28 ,268435456);
        m_l2Power.setItem(29 ,536870912);
        m_l2Power.setItem(30 ,1073741824);
        
        bytDest = new vbarray();
        bytTemp = new vbarray(31);
        bytOut  = new vbarray();
        buff = new vbarray();
        bytMessageDe = new vbarray();
    }
    
    
    public static variant LShift(int lValue, int iShiftBits) throws Exception {
        variant LShift = new variant();
        if (iShiftBits == 0) {
            LShift.set(lValue);
            if(true) return LShift;
        } else if (iShiftBits == 31){
            if (vb.CBool(lValue & 1)) {
                LShift.set(2.147483648E9);
            } else {
                LShift.set(0);
            }
            if(true) return LShift;
        } else if (iShiftBits < 0 || iShiftBits > 31){
            Err.Raise(6);
        }
        if (vb.CBool((lValue & m_l2Power.getItem(31 - iShiftBits).toInt()))) {
            LShift.set(vb.CLng(((lValue & m_lOnBits.getItem(31 - (iShiftBits + 1)).toInt()) * m_l2Power.getItem(iShiftBits).toDouble())) | -2147483648);
        } else {
            LShift.set(((lValue & m_lOnBits.getItem(31 - iShiftBits).toInt()) * m_l2Power.getItem(iShiftBits).toDouble()));
        }
        return LShift;
    }

    public static variant RShift(int lValue, int iShiftBits) throws Exception {
        variant RShift = new variant();
        if (iShiftBits == 0) {
            RShift.set(lValue);
            if(true) return RShift;
        } else if (iShiftBits == 31){
            if (vb.CBool(lValue & -2147483648)) {
                RShift.set(1);
            } else {
                RShift.set(0);
            }
            if(true) return RShift;
        } else if (iShiftBits < 0 || iShiftBits > 31){
            Err.Raise(6);
        }
        RShift.set((lValue & 2147483646) / m_l2Power.getItem(iShiftBits).toInt());
        if (vb.CBool((lValue & -2147483648))) {
            RShift.set((RShift.toInt() | (1073741824 / m_l2Power.getItem(iShiftBits - 1).toInt())));
        }
        return RShift;
    }

    public static variant LShiftByte(variant bytValue, int bytShiftBits) throws Exception {
        variant LShiftByte = new variant();
        if (bytShiftBits == 0) {
            LShiftByte.set(bytValue);
            if(true) return LShiftByte;
        } else if (bytShiftBits == 7){
            if (vb.CBool(bytValue.toInt() & 1)) {
                LShiftByte.set(128);
            } else {
                LShiftByte.set(0);
            }
            if(true) return LShiftByte;
        } else if (bytShiftBits < 0 || bytShiftBits > 7){
            Err.Raise(6);
        }
        LShiftByte.set(((bytValue.toInt() & m_bytOnBits.getItem(7 - bytShiftBits).toInt()) * m_byt2Power.getItem(bytShiftBits).toDouble()));
        return LShiftByte;
    }

    public static variant RShiftByte(variant bytValue, int bytShiftBits) throws Exception {
        variant RShiftByte = new variant();
        if (bytShiftBits == 0) {
            RShiftByte.set(bytValue);
            if(true) return RShiftByte;
        } else if (bytShiftBits == 7){
            if (vb.CBool(bytValue.toInt() & 128)) {
                RShiftByte.set(1);
            } else {
                RShiftByte.set(0);
            }
            if(true) return RShiftByte;
        } else if (bytShiftBits < 0 || bytShiftBits > 7){
            Err.Raise(6);
        }
        RShiftByte.set(bytValue.toInt() / m_byt2Power.getItem(bytShiftBits).toInt());
        return RShiftByte;
    }

    public static int RotateLeft(int lValue, int iShiftBits) throws Exception {
        int RotateLeft = 0;
        RotateLeft = LShift(lValue, iShiftBits).toInt() | RShift(lValue, (32 - iShiftBits)).toInt();
        return RotateLeft;
    }

    public static int RotateLeftByte(variant bytValue, int bytShiftBits) throws Exception {
        int RotateLeftByte = 0;
        RotateLeftByte = LShiftByte(bytValue, bytShiftBits).toInt() | RShiftByte(bytValue, (8 - bytShiftBits)).toInt();
        return RotateLeftByte;
    }

    public static int Pack(vbarray b) throws Exception {
        int lCount = 0;
        int lTemp = 0;
        int Pack = 0;
        for(lCount = 0; lCount <= 3.0; lCount += 1){
            lTemp = b.getItem(lCount).toInt();
            Pack = Pack | LShift(lTemp, (lCount * 8)).toInt();
        }
        return Pack;
    }

    public static int PackFrom(vbarray b, int j) throws Exception {
        int lCount = 0;
        int lTemp = 0;
        int PackFrom = 0;
        for(lCount = 0; lCount <= 3; lCount++){
            lTemp = b.getItem(lCount + j).toInt();
            PackFrom = PackFrom | LShift(lTemp, (lCount * 8)).toInt();
        }
        return PackFrom;
    }

    public static vbarray Unpack(int a, vbarray b) throws Exception {
    	b.setItem(0, ( a & m_lOnBits.getItem(7).toInt() ) );
    	b.setItem(1, RShift( a, 8 ).toInt() & m_lOnBits.getItem(7).toInt() );
    	b.setItem(2, RShift( a, 16 ).toInt() & m_lOnBits.getItem(7).toInt() );
    	b.setItem(3, RShift( a, 24 ).toInt() & m_lOnBits.getItem(7).toInt() );
    	
    	return b;
    }

    public static void UnpackFrom(int a, vbarray buff, int k) throws Exception {
    	buff.setItem((0 + k) , ( a & m_lOnBits.getItem(7).toInt() ) );
    	buff.setItem((1 + k) , RShift( a, 8 ).toInt() & m_lOnBits.getItem(7).toInt() );
    	buff.setItem((2 + k) , RShift( a, 16 ).toInt() & m_lOnBits.getItem(7).toInt() );
    	buff.setItem((3 + k) , RShift( a, 24 ).toInt() & m_lOnBits.getItem(7).toInt() );
    }

    public static variant xtime(variant a) throws Exception {
        int b = 0;
        variant xtime = new variant();
        if (vb.CBool((a.toInt() & 128))) {
            b = 27;
        } else {
            b = 0;
        }
        xtime.set(LShiftByte(a, 1));
        xtime.set(xtime.toInt() ^ b);
        return xtime;
    }

    public static variant bmul(variant x, variant y) throws Exception {
        variant bmul = new variant();
        if (x.unequals(new variant(0)) && y.unequals(new variant(0))) {
            bmul.set(m_ptab.getItem((short)((vb.CLng(m_ltab.getItem(x.toInt())) + vb.CLng(m_ltab.getItem(y.toInt()))) % 255)));
        } else {
            bmul.set(0);
        }
        return bmul;
    }

    public static int SubByte(int a) throws Exception {
        vbarray b = new vbarray();
        vbarray c = new vbarray();
        int SubByte = 0;
        b = new vbarray(3);
        c = new vbarray(3);
        c = Unpack(a, b);
        b.setItem(0 ,m_fbsub.getItem(c.getItem(0).toInt()));
        b.setItem(1 ,m_fbsub.getItem(c.getItem(1).toInt()));
        b.setItem(2 ,m_fbsub.getItem(c.getItem(2).toInt()));
        b.setItem(3 ,m_fbsub.getItem(c.getItem(3).toInt()));
        SubByte = Pack(b);
        return SubByte;
    }

    public static int product(int x, int y) throws Exception {
        vbarray xb = new vbarray();
        vbarray yb = new vbarray();
        int product = 0;
        xb = new vbarray(3);
        yb = new vbarray(3);
        xb = Unpack(x, xb);
        yb = Unpack(y, yb);
        product = bmul(xb.getItem(0), yb.getItem(0)).toInt() ^ bmul(xb.getItem(1), yb.getItem(1)).toInt() ^ bmul(xb.getItem(2), yb.getItem(2)).toInt() ^ bmul(xb.getItem(3), yb.getItem(3)).toInt();
        return product;
    }

    public static int InvMixCol(int x) throws Exception {
        int y = 0;
        int m = 0;
        vbarray b = new vbarray();
        int InvMixCol = 0;
        b = new vbarray(3);
        m = Pack(m_InCo);
        b.setItem(3 ,product(m, x));
        m = RotateLeft(m, 24);
        b.setItem(2 ,product(m, x));
        m = RotateLeft(m, 24);
        b.setItem(1 ,product(m, x));
        m = RotateLeft(m, 24);
        b.setItem(0 ,product(m, x));
        y = Pack(b);
        InvMixCol = y;
        return InvMixCol;
    }

    public static variant ByteSub(int x) throws Exception {
        variant y = new variant();
        variant z = new variant();
        variant ByteSub = new variant();
        z.set(x);
        y.set(m_ptab.getItem(vb.CLng(255.0 - m_ltab.getItem(z.toInt()).toDouble())));
        z.set(y);
        z.set(RotateLeftByte(z, 1));
        y.set(y.toInt() ^ z.toInt());
        z.set(RotateLeftByte(z, 1));
        y.set(y.toInt() ^ z.toInt());
        z.set(RotateLeftByte(z, 1));
        y.set(y.toInt() ^ z.toInt());
        z.set(RotateLeftByte(z, 1));
        y.set(y.toInt() ^ z.toInt());
        y.set(y.toInt() ^ 99);
        ByteSub.set(y);
        return ByteSub;
    }

    public static void gentables() throws Exception {
        int i = 0;
        variant y = new variant();
        vbarray b = new vbarray();
        int ib = 0;
        b = new vbarray(3);
        m_ltab.setItem(0 , 0);
        m_ptab.setItem(0 , 1);
        m_ltab.setItem(1 , 0);
        m_ptab.setItem(1 , 3);
        m_ltab.setItem(3 , 1);
        for(i = 2; i <= 255; i++){
            m_ptab.setItem(i ,m_ptab.getItem(i - 1).toInt() ^ xtime(m_ptab.getItem(i - 1)).toInt());
            m_ltab.setItem(m_ptab.getItem(i).toInt() ,i);
        }
        m_fbsub.setItem(0 , 99);
        m_rbsub.setItem(99 , 0);
        for(i = 1; i <= 255; i++){
            ib = i;
            y.set(ByteSub(ib));
            m_fbsub.setItem(i ,y);
            m_rbsub.setItem(y.toInt() , i);
        }
        y.set(1);
        for(i = 0; i <= 29; i++){
            m_rco.setItem(i ,y);
            y.set(xtime(y));
        }
        for(i = 0; i <= 255; i++){
            y.set(m_fbsub.getItem(i));
            b.setItem(3 ,y.toInt() ^ xtime(y).toInt());
            b.setItem(2 ,y);
            b.setItem(1 ,y);
            b.setItem(0 ,xtime(y));
            m_ftable.setItem(i ,Pack(b));
            y.set(m_rbsub.getItem(i));
            b.setItem(3 ,bmul(m_InCo.getItem(0), y));
            b.setItem(2 ,bmul(m_InCo.getItem(1), y));
            b.setItem(1 ,bmul(m_InCo.getItem(2), y));
            b.setItem(0 ,bmul(m_InCo.getItem(3), y));
            m_rtable.setItem(i ,Pack(b));
        }
    }

    public static void gkey(int nb, int nk, vbarray key) throws Exception {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int N = 0;
        int C1 = 0;
        int C2 = 0;
        int C3 = 0;
        vbarray CipherKey = new vbarray();
        CipherKey = new vbarray(7);
        
        m_Nb = nb;
        m_Nk = nk;
        if (m_Nb >= m_Nk) {
            m_Nr = 6 + m_Nb;
        } else {
            m_Nr = 6 + m_Nk;
        }
        C1 = 1;
        if (m_Nb < 8) {
            C2 = 2;
            C3 = 3;
        } else {
            C2 = 3;
            C3 = 4;
        }
        for(j = 0; j <= (nb - 1); j++){
            m = j * 3;
            
            m_fi.setItem(m , ((j + C1) % nb));
            m_fi.setItem(m + 1 , ((j + C2) % nb));
            m_fi.setItem(m + 2 , ((j + C3) % nb));
            m_ri.setItem(m , ((nb + j - C1) % nb));
            m_ri.setItem(m + 1 , ((nb + j - C2) % nb));
            m_ri.setItem(m + 2 , ((nb + j - C3) % nb));
        }
        N = m_Nb * (m_Nr + 1);
        for(i = 0; i <= (m_Nk - 1); i++){
            j = i * 4;
            CipherKey.setItem(i , PackFrom(key, j));
        }
        for(i = 0; i <= (m_Nk - 1); i++){
            m_fkey.setItem(i , CipherKey.getItem(i));
        }
        j = m_Nk;
        k = 0;
        while (j < N) {
            m_fkey.setItem(j , m_fkey.getItem(j - m_Nk).toInt() ^ SubByte(RotateLeft(m_fkey.getItem(j - 1).toInt(), 24)) ^ m_rco.getItem(k).toInt());
            if (m_Nk <= 6) {
                i = 1;
                while (i < m_Nk && (i + j) < N) {
                    m_fkey.setItem(i + j , m_fkey.getItem(i + j - m_Nk).toInt() ^ m_fkey.getItem(i + j - 1).toInt());
                    i = i + 1;
                }
            } else {
                i = 1;
                while (i < 4 && (i + j) < N) {
                    m_fkey.setItem(i + j , m_fkey.getItem(i + j - m_Nk).toInt() ^ m_fkey.getItem(i + j - 1).toInt());
                    i = i + 1;
                }
                if (j + 4 < N) {
                    m_fkey.setItem(j + 4 , m_fkey.getItem(j + 4 - m_Nk).toInt() ^ SubByte(m_fkey.getItem(j + 3).toInt()));
                }
                i = 5;
                while (i < m_Nk && (i + j) < N) {
                    m_fkey.setItem(i + j , m_fkey.getItem(i + j - m_Nk).toInt() ^ m_fkey.getItem(i + j - 1).toInt());
                    i = i + 1;
                }
            }
            j = j + m_Nk;
            k = k + 1;
        }
        for(j = 0; j <= m_Nb - 1; j++){
            m_rkey.setItem(j + N - nb , m_fkey.getItem(j));
        }
        i = m_Nb;
        while (i < (N - m_Nb)) {
            k = N - m_Nb - i;
            for(j = 0; j <= m_Nb - 1; j++){
                m_rkey.setItem(k + j , InvMixCol(m_fkey.getItem(i + j).toInt()));
            }
            i = i + m_Nb;
        }
        j = N - m_Nb;
        while (j < N) {
            m_rkey.setItem(j - N + m_Nb , m_fkey.getItem(j));
            j = j + 1;
        }
    }

    public static void encrypt(vbarray buff) throws Exception {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        vbarray a = new vbarray();
        vbarray b = new vbarray();
        vbarray x = new vbarray();
        vbarray y = new vbarray();
        vbarray t = new vbarray();
        
        a = new vbarray(7);
        b = new vbarray(7);
        for(i = 0; i <= m_Nb - 1; i++){
            j = i * 4;
            a.setItem(i , PackFrom(buff, j));
            a.setItem(i , a.getItem(i).toInt() ^ m_fkey.getItem(i).toInt());
        }
        k = m_Nb;
        x = a.clone_();
        y = b.clone_();
        for(i = 1; i <= m_Nr - 1; i++){
            for(j = 0; j <= m_Nb - 1; j++){
                m = j * 3;
                y.setItem(j ,m_fkey.getItem(k).toInt() ^ m_ftable.getItem(x.getItem(j).toInt() & m_lOnBits.getItem(7).toInt()).toInt() ^ RotateLeft(m_ftable.getItem(RShift(x.getItem(m_fi.getItem(m).toInt()).toInt(), 8).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 8) ^ RotateLeft(m_ftable.getItem(RShift(x.getItem(m_fi.getItem(m + 1).toInt()).toInt(), 16).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 16) ^ RotateLeft(m_ftable.getItem(RShift(x.getItem(m_fi.getItem(m + 2).toInt()).toInt(), 24).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 24));
                k = k + 1;
            }
            t = x.clone_();
            x = y.clone_();
            y = t.clone_();
        }
        for(j = 0; j <= m_Nb - 1; j++){
            m = j * 3;
            y.setItem(j , m_fkey.getItem(k).toInt() ^ m_fbsub.getItem(x.getItem(j).toInt() & m_lOnBits.getItem(7).toInt()).toInt() ^ RotateLeft(m_fbsub.getItem(RShift(x.getItem(m_fi.getItem(m).toInt()).toInt(), 8).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 8) ^ RotateLeft(m_fbsub.getItem(RShift(x.getItem(m_fi.getItem(m + 1).toInt()).toInt(), 16).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 16) ^ RotateLeft(m_fbsub.getItem(RShift(x.getItem(m_fi.getItem(m + 2).toInt()).toInt(), 24).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 24));
            k = k + 1;
        }
        for(i = 0; i <= m_Nb - 1; i++){
            j = i * 4;
            UnpackFrom(y.getItem(i).toInt(), buff, j); //b.setItem((0 + k)
                        
            x.setItem(i ,0);
            y.setItem(i ,0);
        }
    }

    public static void decrypt(vbarray buff) throws Exception {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        vbarray a = new vbarray();
        vbarray b = new vbarray();
        vbarray x = new vbarray();
        vbarray y = new vbarray();
        vbarray t = new vbarray();
        
        a = new vbarray(7);
        b = new vbarray(7);
        for(i = 0; i <= m_Nb - 1; i++){
            j = i * 4;
            a.setItem(i , PackFrom(buff, j));
            a.setItem(i , a.getItem(i).toInt() ^ m_rkey.getItem(i).toInt());
        }
        k = m_Nb;
        x = a.clone_();
        y = b.clone_();
        for(i = 1; i <= m_Nr - 1; i++){
            for(j = 0; j <= m_Nb - 1; j++){
                m = j * 3;
                y.setItem(j ,m_rkey.getItem(k).toInt() ^ m_rtable.getItem(x.getItem(j).toInt() & m_lOnBits.getItem(7).toInt()).toInt() ^ RotateLeft(m_rtable.getItem(RShift(x.getItem(m_ri.getItem(m).toInt()).toInt(), 8).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 8) ^ RotateLeft(m_rtable.getItem(RShift(x.getItem(m_ri.getItem(m + 1).toInt()).toInt(), 16).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 16) ^ RotateLeft(m_rtable.getItem(RShift(x.getItem(m_ri.getItem(m + 2).toInt()).toInt(), 24).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 24));
                k = k + 1;
            }
            t = x.clone_();
            x = y.clone_();
            y = t.clone_();
        }
        for(j = 0; j <= m_Nb - 1; j++){
            m = j * 3;
            y.setItem(j ,m_rkey.getItem(k).toInt() ^ m_rbsub.getItem(x.getItem(j).toInt() & m_lOnBits.getItem(7).toInt()).toInt() ^ RotateLeft(m_rbsub.getItem(RShift(x.getItem(m_ri.getItem(m).toInt()).toInt(), 8).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 8) ^ RotateLeft(m_rbsub.getItem(RShift(x.getItem(m_ri.getItem(m + 1).toInt()).toInt(), 16).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 16) ^ RotateLeft(m_rbsub.getItem(RShift(x.getItem(m_ri.getItem(m + 2).toInt()).toInt(), 24).toInt() & m_lOnBits.getItem(7).toInt()).toInt(), 24));
            k = k + 1;
        }
        for(i = 0; i <= m_Nb - 1; i++){
            j = i * 4;
            UnpackFrom(y.getItem(i).toInt(), buff, j);
            x.setItem(i ,0);
            y.setItem(i ,0);
        }
    }

    public static boolean IsInitialized(vbarray bytPassword) throws Exception {
        boolean IsInitialized = false;
        
        IsInitialized = vb.IsNumeric(vb.UBound(bytPassword));
        return IsInitialized;
    }

    public static void CopyBytesASP(vbarray bytDest, int lDestStart, vbarray bytSource, int lSourceStart, int lLength) throws Exception {
        int lCount = 0;
        
        do {
            bytDest.setItem((lDestStart + lCount) ,     bytSource.getItem(lSourceStart + lCount));
            lCount = lCount + 1;
        } while(!(lCount == lLength));
    }
    
    public static void CopyBytesASPDe(vbarray bytMessageDe, int lDestStart, vbarray bytSource, int lSourceStart, int lLength) throws Exception {
        int lCount = 0;
        
        do {
        	bytMessageDe.setItem((lDestStart + lCount) ,     bytSource.getItem(lSourceStart + lCount));
            lCount = lCount + 1;
        } while(!(lCount == lLength));
    }

    public static vbarray EncryptData(vbarray bytMessage, vbarray bytPassword) throws Exception {
        vbarray bytKey = new vbarray();
        vbarray bytIn = new vbarray();
        int lCount = 0;
        int lLength = 0;
        int lEncodedLength = 0;
        vbarray bytLen = new vbarray();
        variant lPosition = new variant();
        vbarray EncryptData = new vbarray();
        bytKey = new vbarray(31);
        bytLen = new vbarray(3);
        
        if (!(IsInitialized(bytMessage))) {
            return EncryptData;
        }
        if (!(IsInitialized(bytPassword))) {
            return EncryptData;
        }
        for(lCount = 0; lCount <= vb.UBound(bytPassword); lCount++){
            bytKey.setItem(lCount , bytPassword.getItem(lCount));
            if (lCount == 31) {
                break;
            }
        }
        
        gentables();
        gkey(8, 8, bytKey);
        
        lLength = vb.UBound(bytMessage) + 1;
        lEncodedLength = lLength + 4;
        if (lEncodedLength % 32 != 0) {
            lEncodedLength = lEncodedLength + 32 - (lEncodedLength % 32);
        }
        
        bytIn.redim(lEncodedLength - 1);
        bytOut.redim(lEncodedLength - 1);
        bytIn = Unpack(lLength, bytIn);
        CopyBytesASP(bytIn, 4, bytMessage, 0, lLength);
        for(lCount = 0; lCount <= lEncodedLength - 1; lCount += 32){
            CopyBytesASP(bytTemp, 0, bytIn, lCount, 32);
            encrypt(bytTemp);
            CopyBytesASP(bytOut, lCount, bytTemp, 0, 32);
        }
        
        EncryptData = bytOut.clone_();
        return EncryptData;
    }

    public static vbarray DecryptData(vbarray bytIn, vbarray bytPassword) throws Exception {
        vbarray bytKey = new vbarray();
        int lCount = 0;
        int lLength = 0;
        int lEncodedLength = 0;
        vbarray bytLen = new vbarray();
        variant lPosition = new variant();
        vbarray decryptData = new vbarray();
        bytKey = new vbarray(31);
        bytLen = new vbarray(3);
        
        if (!(IsInitialized(bytIn))) {
            return decryptData;
        }
        if (!(IsInitialized(bytPassword))) {
            return decryptData;
        }
        lEncodedLength = vb.UBound(bytIn) + 1;
        
        if (lEncodedLength % 32 != 0) {
            return decryptData;
        }
        for(lCount = 0; lCount <= vb.UBound(bytPassword); lCount++){
            bytKey.setItem(lCount ,bytPassword.getItem(lCount));
            if (lCount == 31) {
                break;
            }
        }
        gentables();
        gkey(8, 8, bytKey);
        bytOut.redim(lEncodedLength - 1);
        for(lCount = 0; lCount <= lEncodedLength - 1; lCount += 32){
            CopyBytesASP(bytTemp, 0, bytIn, lCount, 32);
            decrypt(bytTemp);
            CopyBytesASP(bytOut, lCount, bytTemp, 0, 32);
        }
        lLength = Pack(bytOut);
        if (lLength > lEncodedLength - 4) {
            return decryptData;
        }
        bytMessageDe.redim(lLength - 1);
        CopyBytesASPDe(bytMessageDe, 0, bytOut, 4, lLength);
        decryptData = bytMessageDe.clone_();
        return decryptData;
    }

    public String AlmoneyEncrypt(String sPlain) throws Exception {
        vbarray bytIn = new vbarray();
        vbarray bytOut = new vbarray();
        vbarray bytPassword = new vbarray();
        int lCount = 0;
        int lLength = 0;
        String sTemp = "";
        String sPassword = "";
        String BASE64_ENCODE = "";
        String BinStr = "";
        String Tmp = "";
        int Padding = 0;
        vbarray ArrayStr = new vbarray();
        int iTemp = 0;
        String almoneyEncrypt = "";
        ArrayStr.redim(vb.Len(sPlain));
        if (sPlain.equals("")) {
            almoneyEncrypt = "";
        } else {
            for(iTemp = 1; iTemp <= vb.Len(sPlain); iTemp += 1){
                ArrayStr.setItem(iTemp ,vb.Int("&H" + vb.Right("0000" + vb.Hex(vb.Asc(vb.Mid(sPlain, iTemp, 1))), 4)));
                Tmp = "";
                while (ArrayStr.getItem(iTemp).Greater(new variant(0))) {
                    Tmp = Tmp + vb.CStr( (ArrayStr.getItem(iTemp).toInt() % 2) );
                    ArrayStr.setItem(iTemp ,vb.Int(ArrayStr.getItem(iTemp).toInt() / 2));
                }
                if (vb.Len(Tmp) > 8) {
                    ArrayStr.setItem(iTemp , vb.StrReverse(Tmp));
                } else {
                    ArrayStr.setItem(iTemp ,vb.Right("00000000" + vb.StrReverse(Tmp), 8));
                }
                BinStr = BinStr + ArrayStr.getItem(iTemp);
            }
            Padding = vb.Len(BinStr) % 3;
            ArrayStr.redim(vb.CInt(vb.Round( (vb.Len(BinStr) / 6) + 0.49))); //6 - 1
            for(iTemp = 0; iTemp <= vb.UBound(ArrayStr); iTemp++){
                ArrayStr.setItem(iTemp ,vb.Left(BinStr, 6));
                if (vb.Len(BinStr) > 6) {
                    BinStr = vb.Right(BinStr, vb.Len(BinStr) - 6);
                }
            }
            
            if (vb.Len(ArrayStr.getItem(vb.UBound(ArrayStr))) != 6) {
                ArrayStr.setItem(vb.UBound(ArrayStr) ,vb.Left(ArrayStr.getItem(vb.UBound(ArrayStr)) + "000000", 6));
            }
            for(iTemp = 0; iTemp <= vb.UBound(ArrayStr); iTemp += 1){
                BASE64_ENCODE = BASE64_ENCODE + BASE64_KEY(new variant(BinToDec(ArrayStr.getItem(iTemp))), "EN");
            }
            for(iTemp = 1; iTemp <= Padding; iTemp++){
                BASE64_ENCODE = BASE64_ENCODE + "=";
            }
            lLength = vb.Len(BASE64_ENCODE);
            sPassword = "AlmoneyEng!";
            bytIn.redim(lLength - 1);
            for(lCount = 1; lCount <= lLength; lCount++){
                bytIn.setItem(lCount - 1 ,vb.CByte(vb.Asc(vb.Mid(BASE64_ENCODE, lCount, 1))));
            }
           
            lLength = vb.Len(sPassword);
            bytPassword.redim(lLength - 1);
            for(lCount = 1; lCount <= lLength; lCount += 1){
                bytPassword.setItem(lCount - 1 ,vb.CByte(vb.Asc(vb.Mid(sPassword, lCount, 1))));
            }
            
            bytOut = EncryptData(bytIn, bytPassword);
            sTemp = "";
            
            for(lCount = 0; lCount <= vb.UBound(bytOut); lCount ++){
                sTemp = sTemp + vb.Right("0" + vb.Hex(bytOut.getItem(lCount)), 2);
            }
            almoneyEncrypt = sTemp;
        }
        return almoneyEncrypt;
    }

    public String AlmoneyDecrypt(String sCypher) throws Exception {
        vbarray bytIn = new vbarray();
        vbarray bytOut = new vbarray();
        vbarray bytPassword = new vbarray();
        int lCount = 0;
        int lLength = 0;
        String sTemp = "";
        String sPassword = "";
        int iTemp = 0;
        variant tmp = new variant();
        String BASE64_DECODE = "";
        variant binStr = new variant();
        vbarray ArrayStr = new vbarray();
        String almoneyDecrypt = "";
        
        if (sCypher.equals("")) {
            almoneyDecrypt = "";
        } else {
            lLength = vb.Len(sCypher);
            sPassword = "AlmoneyEng!";
            bytIn.redim((lLength / 2) - 1);
            for(lCount = 0; lCount <= (lLength / 2) - 1; lCount++){
                bytIn.setItem(lCount ,vb.CByte("&H" + vb.Mid(sCypher, lCount * 2 + 1, 2)));
            }
            lLength = vb.Len(sPassword);
            bytPassword.redim(lLength - 1);
            for(lCount = 1; lCount <= lLength; lCount += 1){
                bytPassword.setItem(lCount - 1 ,vb.CByte(vb.Asc(vb.Mid(sPassword, lCount, 1))));
            }
            
            bytOut = DecryptData(bytIn, bytPassword);
            
            if (vb.IsArray(bytOut)) {
                lLength = vb.UBound(bytOut) + 1;
            } else {
                lLength = 0;
            }
            sTemp = "";
            for(lCount = 0; lCount <= lLength - 1; lCount++){
                sTemp = sTemp + vb.Chr(bytOut.getItem(lCount).toString());
            }
            binStr.set(new variant());
            sTemp = vb.Replace(sTemp, "=", "");
            ArrayStr.redim(vb.Len(sTemp));
            for(iTemp = 1; iTemp <= vb.Len(sTemp); iTemp += 1){
                ArrayStr.setItem(iTemp ,vb.Mid(sTemp, iTemp, 1));
                ArrayStr.setItem(iTemp ,BASE64_KEY(ArrayStr.getItem(iTemp), "DE"));
                tmp.set(new variant());
                while (ArrayStr.getItem(iTemp).Greater(new variant(0))) {
                    tmp.set(tmp + vb.CStr((short)(ArrayStr.getItem(iTemp).toDouble() % 2)));
                    ArrayStr.setItem(iTemp ,vb.Int(ArrayStr.getItem(iTemp).toDouble() / 2));
                }
                binStr.set(binStr + vb.Right("000000" + vb.StrReverse(tmp), 6));
            }
            ArrayStr.redim(vb.CLng(vb.Round(vb.Len(binStr) / 8 - 0 + 0.49)));
            for(iTemp = 0; iTemp <= vb.UBound(ArrayStr); iTemp += 1){
                ArrayStr.setItem(iTemp ,vb.Left(binStr.toString(), 8));
                if (vb.Len(binStr) > 8) {
                    binStr.set(vb.Right(binStr.toString(), vb.Len(binStr) - 8));
                }
            }
            for(iTemp = 0; iTemp < vb.UBound(ArrayStr); iTemp += 1){
                if (ArrayStr.getItem(iTemp).Greater(new variant(0))) {
                    if (vb.Asc(ArrayStr.getItem(iTemp).toString()) == 48) {
                        BASE64_DECODE = BASE64_DECODE + vb.Chr(BinToDec(ArrayStr.getItem(iTemp)));
                    } else {
                        BASE64_DECODE = BASE64_DECODE + vb.Chr(BinToDec(new variant(ArrayStr.getItem(iTemp) + "" + ArrayStr.getItem(iTemp + 1))));
                        iTemp = iTemp + 1;
                    }
                }
            }
            almoneyDecrypt = BASE64_DECODE;
        }
        return almoneyDecrypt;
    }

    //Base64 KEY
    public static variant BASE64_KEY(variant Num, String Flag) throws Exception {
        Num = new variant(Num);
        String Key = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        variant BASE64_KEY = new variant();
        if (Flag.toUpperCase().equals("EN")) {
            BASE64_KEY.set(vb.Mid(Key, Num.add(new variant(1)).toDouble(), 1));
        } else if (Flag.toUpperCase().equals("DE")){
            BASE64_KEY.set(vb.InStr(Key, Num.toString()) - 1);
        }
        return BASE64_KEY;
    }

    //2진수 -> 10진수
    public static double BinToDec(variant strBin) throws Exception {
        int lTot = 0;
        int ctr = 0;
        int nLen = 0;
        int BinToDec = 0;
        nLen = vb.Len(strBin);
        for(ctr = nLen - 1; ctr >= 1;ctr--){
            lTot = (int) (lTot + Math.pow((2 * vb.CInt(vb.Mid(strBin.toString(), ctr, 1))), (nLen - ctr)));
        }
        BinToDec = lTot + vb.CInt(vb.Mid(strBin.toString(), nLen, 1));
        return BinToDec;
    }
}
