package maheshchennaboina;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;
import javazoom.jl.player.Player;
public class Audioplayer {
	public static void main(String[] args) throws IOException,NoSuchElementException {
		Scanner in=new Scanner(System.in);
		String pathName="C:\\Users\\Mahesh chennaboina\\Music\\Music";
		File f=new File(pathName);
		Mp3Files file=new Mp3Files(pathName);
		file.start();
		Mp3Files.exit=false;
		String option;
		boolean isexit=false;
		int opt=0;
		do {
			System.out.printf("\n\n\n\t\t\t\t\t\t      %c  Music Player  %c \n",182,182);
			System.out.print("\t\t\t                         ---------------------------\n\n");
			System.out.println("\t\t\t\t\t1. Show PlayList ");
			System.out.println("\t\t\t\t\t2. Loop Playlist");
			System.out.println("\t\t\t\t\t3. Play a Song");
			System.out.println("\t\t\t\t\t4. Exit ");
			System.out.print("\t\t\t\t Choose Your option :  ");
			option=in.next();
			Mp3Files.exit=false;
			try {
				opt=Integer.parseInt(option);
			}catch(NumberFormatException e) {
				opt=0;
			}
			switch(opt) {
			
			case 1:
				file.playList();
				break;
			case 2:
				try {
					file.loop();	
				}catch(IOException e) {
					
				}
				break;
			case 3:
				file.play();
				break;
			case 4:
				isexit=true;
				System.out.println("\t\t\t\t            Thank you for listening the Music..... ");
				//System.exit(0);
				break;
			default:
				System.out.println("\t\t\t\t         Not a Valid Option choose once again ");
			}
			
		}while(!isexit);
	}
}
class  MusicPlayer{
	static boolean loop=false;
	private int songNumber;
	private String file;
	private boolean canResume;
	private boolean valid;
	private Player player;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private int total;
	private int stopped;
	static boolean value=false;
	MusicPlayer(String path){
		this.file=path;
		canResume=valid=false;
		total=stopped=0;
		bis=null;
		fis=null;
		player=null;
	}
	public boolean play(int pos,int songNumber) {
		this.songNumber=songNumber;
		valid=true;
		canResume=false;
		try {
			fis=new FileInputStream(file);
			total=fis.available();
			if(pos>-1) {
				fis.skip(pos);
			}
			bis=new BufferedInputStream(fis);
			player=new Player(bis);
			new Thread() {
				public void run() {
					try {
						player.play();
						
					}
					catch(Exception e) {
						System.out.println(e.getMessage());
						valid=false;
					}
				}
			}.start();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			valid=false;
		}
		return valid;
	}
	public boolean canResume() {
		return canResume;
	}
	public void pause() {
		try {
			if(fis!=null) {
				System.out.println("\t\t\t\t                    Pausing...   ):");
				stopped=fis.available()+10000;
				player.close();
				fis=null;
				bis=null;
				if(valid) {
					canResume=true;
				}	
			}else {
				System.out.println("\t\t\t\t\t     Already Paused...");
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void resume() {
		if(!canResume) {
			System.out.println("\t\t\t\t\t     Already Resumed...");
			System.out.println("");
			return ;
		}
		System.out.println("\t\t\t\t                    Resuming...    :)");
		if(play(total-stopped,songNumber)) {
			canResume=false;
		}
	}
	public void close() {
		try {
			if(player!=null) {
				player.close();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public int howMuchAvailable() {
		// TODO Auto-generated method stub
		return 0;
	}
}
class Mp3Files{
	static boolean exit =false;
	private String pathName;
	private int songsLength;
	static int number=0;
	String str[];
	File arr[];
	Mp3Files(String pathName){
		this.pathName=pathName;
		File f=new File(this.pathName);
		arr=f.listFiles();
		this.songsLength=arr.length;
		str=new String[arr.length];
	}
	public void start() {
		number=0;
		try {
			for (File fr:arr) {
				if(fr.exists() && (fr.getName().endsWith(".mp3") || fr.getName().endsWith(" .mp3"))) {
					str[number++]=fr.getName();
				}
			}
		}catch(Exception e) {
			System.out.println("    Some exception arised while reading mp3 files");
			System.out.println(e.getMessage());
			
		}	
	}
	public void playList() {
		try {
			if(true) {
			
				System.out.printf("\n\n\t\t\t                                    MP3 PlayList :!\n");
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("                              \t\t       Number         \t     Song Name             ");
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
				for(int i=0;i<number;i++) {
					System.out.println("                  \t    \t                 "+(i+1)+"      \t          "+str[i]);
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void play() throws IOException {
		pathName="C:\\Users\\Mahesh chennaboina\\Music\\Music";
		Scanner in=new Scanner(System.in);
		System.out.print("\t\t\t\t\tChoose The Song Number : ");
		String s=in.next();
		boolean b=false;
		int k=0;
		try {
			k=Integer.parseInt(s);
		}catch(NumberFormatException e) {
			b=true;
			System.out.println("  \t\t                                  Please choose a valid option  ");
			play();
		}
		if(b==true) {
			return;
		}
		if(k<=0 || k>number) {
			System.out.println("\t\t\t \t\t        IllegalNumberRange Exception arised  :(");
			play();
			
		}else if(k>0 && k<=number){
			pathName+="\\";
			pathName+=str[k-1];
			MusicPlayer c=new MusicPlayer(pathName);
			System.out.println("\t\t\t                      Playing  \""+str[k-1]+"...\"");
			String option="";
			int opt=0;
			c.play(-1,k);
			do {
				//System.out.println(" K value is "+k);
				System.out.print("\n\n\t\t\t\t \t\t  Select Options ! \n \t\t\t \t\t    --------------------------\t\n\t\t\t\t\t1. Play Next\n\t\t\t\t\t2. Play Previous\n\t\t\t\t\t3. Pause \n\t\t\t\t\t4. Resume \n\t\t\t\t\t5. Out of Current song \n\t\t\t\tChooose your option : ");
			    option=in.next();
				
				try {
					opt=Integer.parseInt(option);
				}catch(NumberFormatException e) {
					System.out.println("\t\t\t\t                    Not a Valid option. Choose again");
					opt=0;
					exit=false;
				}
					switch(opt) {
					case 0:
						break;
					case 1:
						c.close();
						playNext(k+1);
						break;
					case 2:
						c.close();
						playPrev(k-1);
						break;
					case 3:
						c.pause();
						break;
					case 4:
						c.resume();
						break;
					case 5:
						exit=true;
						c.close();
						System.out.println("\t\t\t\t                    Getting out of Current Song ):-");
						break;
					default:
						exit=false;
						//System.out.println("\t\t\t\t                    Not a Valid option. Choose again");
						System.out.println("\t\t\t \t        IllegalNumberRange Exception arised  :(");
						break;
					}
				
			}while(!exit);
		}
	}
	public void playNext(int song) throws IOException,NoSuchElementException {
		if(song>number) {
			song=1;
		}
		pathName="C:\\Users\\Mahesh chennaboina\\Music\\Music";
		pathName+="\\";
		pathName+=str[song-1];
		MusicPlayer1 c=new MusicPlayer1(pathName);
		System.out.println("\t\t\t                            Playing  \""+str[song-1]+"...\"");
		String option="";
		int opt=0;
		Scanner in=new Scanner(System.in);
		c.play(-1,song);
		if(MusicPlayer.loop==true) {
           FileInputStream fs=null;
			try {
				fs=new FileInputStream(pathName);
				BufferedInputStream bs=new BufferedInputStream(fs);
				System.out.println();
				
			}catch(IOException e ) {
				System.out.println(e.getMessage());
			}
			    String opt1="";
			    
				while(c.howMuchAvailable()!=0) {
					System.out.println("c.howmuchavailable = "+c.howMuchAvailable());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(c.howMuchAvailable()==0) {
					System.out.println("Currently in PlayNext() ");
					 loop(song+1);
				}else {
					System.out.println("Terminated\n");
					//System.exit(0);
					return;
				}
		}else {
			do {
				System.out.print("\n\n\t\t\t\t \t\t  Select Options ! \n \t\t\t \t\t    --------------------------\t\n\t\t\t\t\t1. Play Next\n\t\t\t\t\t2. Play Previous\n\t\t\t\t\t3. Pause \n\t\t\t\t\t4. Resume \n\t\t\t\t\t5. Out of Current song \n\t\t\t\tChooose your option : ");
				option=in.next();
				if(option.isEmpty()) {
					System.out.println("Empty Input ");
					playNext(song+1);
				}
				try {
					opt=Integer.parseInt(option);
				}catch(NumberFormatException e) {
					System.out.println("\t\t\t\t                    Not a Valid option. Choose again");
					exit=false;
					opt=0;
				}
				if(true) {
					switch(opt) {
					case 0:
						break;
					case 1:
						c.close();
						playNext(song+1);
						break;
					case 2:
						c.close();
						playPrev(song-1);
						break;
					case 3:
						c.pause();
						break;
					case 4:
						c.resume();
						break;
					case 5:
						exit=true;
						c.close();
						System.out.println("\t\t\t\t                    Getting out of Current Song ):-");
						break;
					default:
						exit=false;
						System.out.println("\t\t\t \t        IllegalNumberRange Exception arised  :(");
						break;
					}
				}
			}while(!exit);
		}
		
	}
	public void playPrev(int song) throws IOException {
		pathName="C:\\Users\\Mahesh chennaboina\\Music\\Music";
		pathName+="\\";
		if(song==0) {
			song=number;
		}
		pathName+=str[song-1];
		MusicPlayer c=new MusicPlayer(pathName);
		System.out.println("\t\t\t                      Playing  \""+str[song-1]+"...\"");
		String option;
		int opt=0;
		Scanner in=new Scanner(System.in);
		c.play(-1,song);
		do {
			System.out.print("\n\n\t\t\t\t \t\t  Select Options ! \n \t\t\t \t\t    --------------------------\t\n\t\t\t\t\t1. Play Next\n\t\t\t\t\t2. Play Previous\n\t\t\t\t\t3. Pause \n\t\t\t\t\t4. Resume \n\t\t\t\t\t5. Out of Current song \n\t\t\t\tChooose your option : ");
			option=in.next();
			try {
				opt=Integer.parseInt(option);
			}catch(NumberFormatException e) {
				exit=false;
				System.out.println("\t\t\t\t                    Not a Valid option. Choose again");
				opt=0;
			}
			if(true) {
				switch(opt) {
				case 0:
					break;
					
				case 1:
					c.close();
					playNext(song+1);
					break;
				case 2:
					c.close();
					playPrev(song-1);
					break;
				case 3:
					c.pause();
					break;
				case 4:
					c.resume();
					break;
				case 5:
					exit=true;
					c.close();
					System.out.println("\t\t\t\t                    Getting out of Current Song ):-");
					break;
				default:
					System.out.println("\t\t\t \t        IllegalNumberRange Exception arised  :(");
					exit=false;
					break;
				}
			}
		}while(!exit);
	}
	public void loop() throws IOException,NoSuchElementException {
		Scanner in=new Scanner(System.in);
		String s;
		int intial;
		System.out.print("\t\t\t\t\tEnter Intial Song number ? ");
		s=in.next();
		int option=0;
		try {
			option=Integer.parseInt(s);
			
		}catch(NumberFormatException e) {
			System.out.println(e.getMessage());
			option=0;
			loop();
			return;	
		}
		
		if(option>=1 && option<number) {
			MusicPlayer.loop=true;
			String pathName="C:\\Users\\Mahesh chennaboina\\Music\\Music";
			pathName+="\\";
			pathName+=str[option-1];
			MusicPlayer1 c=new MusicPlayer1(pathName);
			System.out.println("\t\t\t                            Playing  \""+str[option-1]+"...\"");
			c.play(-1, option);
			FileInputStream fs=null;
			
			try {
				fs=new FileInputStream(pathName);
				BufferedInputStream bs=new BufferedInputStream(fs);
				System.out.println();
				
			}catch(IOException e ) {
				System.out.println(e.getMessage());
			}
		    int opt;
		    String s1="";
			while(c.howMuchAvailable()!=0) {
				//System.out.println("Mahesh");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			if(c.howMuchAvailable()==0) {
				System.out.println("Currently in loop() ");
				//System.out.println("Mahesh chennaboina ");
				playNext(option+1);
			}
					
		}
		return;	
	}
	public void loop(int option) throws IOException,NoSuchElementException {
		if(option>number) {
			option=1;
		}
		String pathName="C:\\Users\\Mahesh chennaboina\\Music\\Music";
		pathName+="\\";
		pathName+=str[option-1];
		MusicPlayer1 c=new MusicPlayer1(pathName);
		System.out.println("\t\t\t                            Playing  \""+str[option-1]+"...\"");
		c.play(-1, option);
		FileInputStream fs=null;
		
		try {
			fs=new FileInputStream(pathName);
			BufferedInputStream bs=new BufferedInputStream(fs);
			System.out.println();
			
		}catch(IOException e ) {
			
			System.out.println(e.getMessage());
		}
		Scanner in=new Scanner(System.in);
	
		    int opt;
		    String opt2="";
		   
			while(c.howMuchAvailable()!=0) {
				try {
			
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			if(c.howMuchAvailable()==0) {
				System.out.println("Currently in loop(option) ");
				playNext(option+1);
			}
	}
}
class  MusicPlayer1{
	private int songNumber;
	private String file;
	private boolean canResume;
	private boolean valid;
	private Player player;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private int total;
	private int stopped;
	static boolean value=false;
	MusicPlayer1(String path){
		this.file=path;
		canResume=valid=false;
		total=stopped=0;
		bis=null;
		fis=null;
		player=null;
	}
	public boolean play(int pos,int songNumber) {
		this.songNumber=songNumber;
		valid=true;
		canResume=false;
		try {
			fis=new FileInputStream(file);
			total=fis.available();
			if(pos>-1) {
				fis.skip(pos);
			}
			bis=new BufferedInputStream(fis);
			player=new Player(bis);
			new Thread() {
				public void run() {
					try {
						player.play();
						
					}
					catch(Exception e) {
						System.out.println(e.getMessage());
						valid=false;
					}
				}
				public int mahi() throws IOException {
					return fis.available();
					
				}
			}.start();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			valid=false;
		}
		return valid;
	}
	public boolean canResume() {
		return canResume;
	}
	public void pause() {
		try {
			if(fis!=null) {
				System.out.println("\t\t\t\t                    Pausing...   ):");
				stopped=fis.available()+10000;
				player.close();
				fis=null;
				bis=null;
				if(valid) {
					canResume=true;
				}	
			}else {
				System.out.println("\t\t\t\t\t     Already Paused...");
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public int howMuchAvailable() throws IOException {
		if(fis.available()==0) {
			return 0;
		}else {
			return 1;
		}
	}
	public void resume() {
		if(!canResume) {
			System.out.println("\t\t\t\t\t     Already Resumed...");
			System.out.println("");
			return ;
		}
		System.out.println("\t\t\t\t                    Resuming...    :)");
		if(play(total-stopped,songNumber)) {
			canResume=false;
		}
	}
	public void close() {
		try {
			if(player!=null) {
				player.close();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
