import service.DataLogService;

public class Main {

    public static void main(String[] args){
        DataLogService dataLogService = new DataLogService();
        dataLogService.processLogFile(args[0]);
    }
}
