package foo;

import java.io.*;

public class Escribir {
	
	private String path;
	private File archivo;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	
	public FileWriter getFileWriter() {
		return this.fileWriter;
	}

	public void setFileWriter(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}

	public BufferedWriter getBufferedWriter() {
		return this.bufferedWriter;
	}

	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	public File getArchivo() {
		return this.archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public Escribir(String path) throws IOException{
		this.setPath(path);
		this.setArchivo(new File(this.getPath()));
		try{
			if(!this.getArchivo().exists())
				this.getArchivo().createNewFile();
			this.setFileWriter(new FileWriter(this.getPath(),true));
			this.setBufferedWriter(new BufferedWriter(this.getFileWriter()));
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public  void escribir(String input){
		try {
			this.getBufferedWriter().write(input);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try{
			if(this.getBufferedWriter() != null)
				this.getBufferedWriter().close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}