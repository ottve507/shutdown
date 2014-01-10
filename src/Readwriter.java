import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Readwriter {
	public void write(String time, int opt) throws IOException{
	
		if(opt==1){
		BufferedWriter write = new BufferedWriter(new FileWriter("shutdown.scpt"));
		write.write("do shell script \"sudo shutdown -h +" +time+ "\" with administrator privileges");
		write.newLine();
		write.close();}
		else{
			BufferedWriter write = new BufferedWriter(new FileWriter("shutdown.scpt"));
			write.write(time);
			write.newLine();
			write.close();}
		}
	
	}
