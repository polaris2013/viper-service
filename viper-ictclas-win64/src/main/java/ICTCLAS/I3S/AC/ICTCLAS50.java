package ICTCLAS.I3S.AC;

import org.whuims.irlab.viper.common.CFGKEYS;
import org.whuims.irlab.viper.common.ViperConfig;

public class ICTCLAS50 {

    enum eCodeType {
        CODE_TYPE_UNKNOWN, // 未知,系统自动识别, 0
        CODE_TYPE_ASCII, // ASCII, 1
        CODE_TYPE_GB, // GB2312,GBK, gb18030, 2
        CODE_TYPE_UTF8, // UTF-8, 3
        CODE_TYPE_BIG5 // BIG5,  4
    };

    public native boolean ICTCLAS_Init(byte[] sPath);

    public native boolean ICTCLAS_Exit();

    public native int ICTCLAS_ImportUserDictFile(byte[] sPath, int eCodeType);

    public native int ICTCLAS_SaveTheUsrDic();

    public native int ICTCLAS_SetPOSmap(int nPOSmap);

    public native boolean ICTCLAS_FileProcess(byte[] sSrcFilename, int eCodeType, int bPOSTagged, byte[] sDestFilename);

    public native byte[] ICTCLAS_ParagraphProcess(byte[] sSrc, int eCodeType, int bPOSTagged);

    public native byte[] nativeProcAPara(byte[] sSrc, int eCodeType, int bPOStagged);

    /* Use static intializer */
    static {
        ViperConfig config=ViperConfig.getInstance();
        System.load(config.getPropValue(CFGKEYS.ICTCLAS_DLL,""));
//     System.loadLibrary("ICTCLAS50");
    }
}
