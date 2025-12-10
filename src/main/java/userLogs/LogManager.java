package userLogs;

import RunTimeLog.RuntimeLogger;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class LogManager
{
    private static LogWrapper log=new LogWrapper();
    private static String file ="LocalDataBase/userLog.xml";

    public static void addLog(String message)
    {
        getLogDataBase();
        log.addLogEntry(new LogEntry(message));
        updateLogDataBase();
    }

    private static void updateLogDataBase()
    {
        try
        {
            JAXBContext jaxbContext=JAXBContext.newInstance(LogWrapper.class);
            Marshaller marshaller=jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(log,new File(file));



        }catch(JAXBException e)
        {
            RuntimeLogger.logger.error(e.getMessage(),e);
        }
    }

    private static void getLogDataBase()
    {
        try
        {
            JAXBContext jaxbContext=JAXBContext.newInstance(LogWrapper.class);
            Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
             File f= new File(file);
            log=(LogWrapper)unmarshaller.unmarshal(f);
        } catch (JAXBException e) {
            RuntimeLogger.logger.error(e.getMessage(),e);
        }
    }
    public static void printLogs()
    {
        getLogDataBase();
        log.printLog();
    }





}
