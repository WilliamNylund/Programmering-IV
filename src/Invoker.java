
import java.util.ArrayList;
import java.util.List;

public class Invoker {
	
	private final List<Command> commands = new ArrayList();
	private final List<Command> redos = new ArrayList();
	
	public void execute(Command command) {
		commands.add(command);
		System.out.println(commands.size());
		command.execute();
	}
	
	public void undo() { 
		Command command = commands.get(commands.size()-1); 
		redos.add(command);
		commands.remove(commands.size()-1);
		command.undo();
	}
	
	public void redo() {
		Command command = redos.get(redos.size()-1);
		commands.add(command);
		redos.remove(redos.size()-1);
		command.redo();
	}
	
	public int getCommandList(){
		return commands.size();
	}
	
	public int getRedoList(){
		return redos.size();
	}
}
