package gq.jingge.blog.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IpParser {
    /**
     * @Fields DbPath : IP库的路径，默认在根目录
     */
    private String DbPath = "qqwry.dat";
    /**
     * @Fields Country : 设置国家，和本地实例
     */
    private String Country, LocalStr;
    /**
     * @Fields IPN : 对IP搜索和比较，转换的表现方式，一般是转换成long类型进行比较
     */
    private long IPN;
    /**
     * @Fields RecordCount : 记录数，和国家标识，用于判断。
     */
    private int RecordCount, CountryFlag;
    /**
     * @Fields StartIP : IP数据记录判别计算声明。
     */
    private long RangE, RangB, OffSet, StartIP, EndIP, FirstStartIP,
            LastStartIP, EndIPOff;
    /**
     * @Fields fis : 文件读取类
     */
    private RandomAccessFile fis;
    /**
     * @Fields buff :  声明默认的二进制buff。
     */
    private byte[] buff;

    /**
     * @Description: 将字节转换成Long
     * @param b
     * @return long
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    private long ByteArrayToLong(byte[] b) {
        long ret = 0;
        for (int i = 0; i < b.length; i++) {
            long t = 1L;
            for (int j = 0; j < i; j++)
                t = t * 256L;
            ret += ((b[i] < 0) ? 256 + b[i] : b[i]) * t;
        }
        return ret;
    }

    /**
     * @Description: 将IP字符串转换成Int类型。
     * @param ip
     * @return long
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    private long ipStrToInt(String ip) {
        String[] arr = ip.split("\\.");
        long ret = 0;
        for (int i = 0; i < arr.length; i++) {
            long l = 1;
            for (int j = 0; j < i; j++)
                l *= 256;
            try {
                ret += Long.parseLong(arr[arr.length - i - 1]) * l;
            } catch (Exception e) {
                ret += 0;
            }
        }
        return ret;
    }

    /**
     * @Description: 在IP地址库进行搜索
     * @param @param ip
     * @param @throws Exception
     * @return void
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    public void seek(String ip) throws Exception {
        this.IPN = ipStrToInt(ip);
        fis = new RandomAccessFile(this.DbPath, "r");
        buff = new byte[4];
        fis.seek(0);

        fis.read(buff);
        FirstStartIP = this.ByteArrayToLong(buff);
        fis.read(buff);
        LastStartIP = this.ByteArrayToLong(buff);
        RecordCount = (int) ((LastStartIP - FirstStartIP) / 7);
        if (RecordCount <= 1) {
            LocalStr = Country = "未知";
            throw new Exception(ip + " is unkown , FirstStartIP:"
                    + FirstStartIP + " LastStartIP:" + LastStartIP + "buff "
                    + buff[0]);
        }

        RangB = 0;
        RangE = RecordCount;
        long RecNo;

        do {
            RecNo = (RangB + RangE) / 2;
            getStartIP(RecNo);
            if (IPN == StartIP) {
                RangB = RecNo;
                break;
            }
            if (IPN > StartIP)
                RangB = RecNo;
            else
                RangE = RecNo;
        } while (RangB < RangE - 1);

        getStartIP(RangB);
        getEndIP();
        getCountry(IPN);

        fis.close();
    }

    /**
     * @Description: 获得IP标记
     * @param OffSet
     * @return
     * @param @throws IOException
     * @return String
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    private String getFlagStr(long OffSet) throws IOException {
        int flag = 0;
        do {
            fis.seek(OffSet);
            buff = new byte[1];
            fis.read(buff);
            flag = (buff[0] < 0) ? 256 + buff[0] : buff[0];
            if (flag == 1 || flag == 2) {
                buff = new byte[3];
                fis.read(buff);
                if (flag == 2) {
                    CountryFlag = 2;
                    EndIPOff = OffSet - 4;
                }
                OffSet = this.ByteArrayToLong(buff);
            } else
                break;
        } while (true);

        if (OffSet < 12) {
            return "";
        } else {
            fis.seek(OffSet);
            return getStr();
        }
    }

    /**
     * @Description: 获得字符串
     * @return
     * @throws IOException
     * @return String
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    private String getStr() throws IOException {
        long l = fis.length();
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        byte c = fis.readByte();
        do {
            byteout.write(c);
            c = fis.readByte();
        } while (c != 0 && fis.getFilePointer() < l);
        return byteout.toString();
    }

    /**
     * @Description: 根据ip获取国家位置
     * @param ip
     * @throws IOException
     * @return void
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    private void getCountry(long ip) throws IOException {
        if (CountryFlag == 1 || CountryFlag == 2) {
            Country = getFlagStr(EndIPOff + 4);
            if (CountryFlag == 1) {
                LocalStr = getFlagStr(fis.getFilePointer());
                if (IPN >= ipStrToInt("255.255.255.0")
                        && IPN <= ipStrToInt("255.255.255.255")) {
                    LocalStr = getFlagStr(EndIPOff + 21);
                    Country = getFlagStr(EndIPOff + 12);
                }
            } else {
                LocalStr = getFlagStr(EndIPOff + 8);
            }
        } else {
            Country = getFlagStr(EndIPOff + 4);
            LocalStr = getFlagStr(fis.getFilePointer());
        }
    }

    /**
     * @Description: 获得最后IP
     * @return
     * @throws IOException
     * @return long
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    private long getEndIP() throws IOException {
        fis.seek(EndIPOff);
        buff = new byte[4];
        fis.read(buff);
        EndIP = this.ByteArrayToLong(buff);
        buff = new byte[1];
        fis.read(buff);
        CountryFlag = (buff[0] < 0) ? 256 + buff[0] : buff[0];
        return EndIP;
    }

    /**
     * @Description: 获得起始IP
     * @param RecNo
     * @return
     * @throws IOException
     * @return long
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    private long getStartIP(long RecNo) throws IOException {
        OffSet = FirstStartIP + RecNo * 7;
        fis.seek(OffSet);
        buff = new byte[4];
        fis.read(buff);
        StartIP = this.ByteArrayToLong(buff);
        buff = new byte[3];
        fis.read(buff);
        EndIPOff = this.ByteArrayToLong(buff);
        return StartIP;
    }

    /**
     * @Description: 获得当地地址
     * @return String
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    public String getLocal() {
        return this.LocalStr;
    }

    /**
     * @Description: 获得国家
     * @param @return
     * @return String
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    public String getCountry() {
        return this.Country;
    }

    /**
     * @Description: 获取路径信息
     * @param @param path
     * @return void
     * @throws
     * @author 王宏
     * @date 2017年11月20日
     */
    public void setPath(String path) {
        this.DbPath = path;
    }

    public static void main(String[] args) throws Exception {
        IpParser w = new IpParser();
        // w.setPath(new File("QQWry2.Dat").getAbsolutePath());
        w.seek("59.39.253.251");
        System.out.println(w.getCountry() + " " + w.getLocal());
    }

    /**
     * @Description: IP转换
     * @param ipStr
     * @throws Exception
     * @return String
     * @author 王宏
     * @date 2017年11月20日
     */
    public String parse(String ipStr) throws Exception {
        this.seek(ipStr);
        return this.getCountry() + " " + this.getLocal();
    }
}