import Exceptions.InvalidArgumentsException;

public class DbWrapper {

    public static void main(String[] args) throws Exception {
        if (args == null) {
            throw new Exception("Invalid argument length");
        }
        if (args[0].equals(Constants.INIT)) {
            if (args.length != 2) {
                throw new InvalidArgumentsException("init command expects 1 parameter but got" + (args.length - 1));
            }
            InitCommand init = new InitCommand(new MySQLDBManager(), args[1]);
            init.run();
            return;
        }
        if (args[0].equals(Constants.QUERY)) {
            if (args.length != 3) {
                throw new InvalidArgumentsException("query command expects 2 parameters but got" + (args.length - 1));
            }
            QueryCommand command = new QueryCommand(new MySQLDBManager(), args[1], args[2]);
            command.run();
            return;
        }
        throw new InvalidArgumentsException("Init or Query command Expected");
    }
}
