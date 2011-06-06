import java.io.IOException;


public class Client {

	/**
	 * @param args
	 */
	public static void main (String[] args) {
	  try{
        Model model = new Model();
		Connection connection = new Connection(model);
        model.setConnection(connection);
      } catch (IOException e) {
	    System.out.print(e);
	  }
	}

}
