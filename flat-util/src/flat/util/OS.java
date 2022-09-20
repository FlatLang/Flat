package flat.util;

public class OS {
    public static final int	OS;

    public static final String EXECUTABLE_EXTENSION, DYNAMIC_LIB_EXT;

    public static final int		WINDOWS       = 1;
    public static final int		MACOSX        = 2;
    public static final int		LINUX         = 3;

    static
    {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.startsWith("win"))
        {
            OS = WINDOWS;
            EXECUTABLE_EXTENSION = ".exe";
            DYNAMIC_LIB_EXT  = ".dll";
        }
        else if (osName.startsWith("mac"))
        {
            OS = MACOSX;
            EXECUTABLE_EXTENSION = "";
            DYNAMIC_LIB_EXT  = ".dylib";
        }
        else if (osName.startsWith("lin"))
        {
            OS = LINUX;
            EXECUTABLE_EXTENSION = "";
            DYNAMIC_LIB_EXT  = ".so";
        }
        else
        {
            OS = 0;
            EXECUTABLE_EXTENSION = "";
            DYNAMIC_LIB_EXT  = "";
        }
    }
}
