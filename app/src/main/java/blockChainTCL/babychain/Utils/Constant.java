package blockChainTCL.babychain.Utils;

public class Constant {

    // IP주소는 하단 값과 network_security_config.xml 값 2개 수정 필요
    public static final String BACKEND_URL = "http://10.0.2.2:3500/api/v1/babychain/";

    public static final String RESISTER = "register";
    public static final String READ = "read";
    public static final String MODIFY = "modify";
    public static final String DELETE = "delete";
    public static final String UPLOAD_IMAGE = "uploadImage";
    public static final String READ_IMAGE = "readImage";
    public static final String UPLOAD_IMAGE_TO_TEXT = "uploadImageToText";
    public static final String READ_IMAGE_TO_TEXT = "readImageToText";
    public static final String MODIFY_IMAGE_TO_TEXT = "modifyImageToText";
    public static final String DELETE_IMAGE_TO_TEXT = "deleteImageToText";

    public static final String UPLOAD_FOR_REGISTERED = "uploadImageAndValuesForRegistered";
    public static final String READ_FOR_REGISTERED = "readImageAndValuesForRegistered";
    public static final String UPLOAD_FOR_PROTECTED = "uploadImageAndValuesForProtected";
    public static final String READ_FOR_PROTECTED = "readImageAndValuesForProtected";
    public static final String UPLOAD_FOR_MISSING = "uploadImageAndValuesForMissing";
    public static final String READ_FOR_MISSING = "readImageAndValuesForMissing";

    public static final String TYPE_STRING = "s";
    public static final String TYPE_FILE = "f";

    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;
}
