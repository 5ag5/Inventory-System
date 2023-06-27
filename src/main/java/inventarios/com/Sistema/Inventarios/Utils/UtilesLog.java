package inventarios.com.Sistema.Inventarios.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilesLog {

    private static Logger log = LogManager.getLogger(UtilesLog.class);

    @SuppressWarnings("rawtypes")
    public static void registratinInfo(Class classToRegister,LogType logType,String message){
        log =  LogManager.getLogger(classToRegister);

        switch (logType)
        {
            case DEBUG:
                log.debug(message);
                break;
            case ERROR:
                log.error(message);
                break;
            case FATAL:
                log.fatal(message);
                break;
            case INFO:
                log.info(message);
                break;
            case WARNING:
                log.warn(message);
        }

    }

}
