
import javax.sound.sampled.*;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
public class FFT {

	private static JTextField entry;
    private static JLabel jLabel1;
    private static JScrollPane jScrollPane1;
	static StringBuilder result = new StringBuilder();
    private static JLabel status;
    private static JTextArea textArea;
	private static Font font;
	private static JFrame frame;
    final Color entryBg;
    final Highlighter hilit;
    final Highlighter.HighlightPainter painter;
    final static Color  HILIT_COLOR = Color.GREEN;
    final static Color  ERROR_COLOR = Color.ORANGE;
    final static String RESTART_ACTION = "restart";
	private static double SampleFrequency = 44100.0;
	private static double Sample = 16384.0;
	private static double Resolution = 0;
	private static double AmplitudeBar = 10000000.0;//24000000
	private static double pretemp = 0;
	private	static double prefrequency = 0;
	public static int position = 0;
	private static String pres = "C200";
    private static final String[] NAME = { "A4",    "B4",    "C5",   "D5",    "E5",   "F5",    "G5",    "A5",   "B5",    
    	"C6",   "D6",   "E6",   "F6",   "G6",   "A6",   "B6",   "C7",   "D7",   "E7",   "F7",   "G7",   "A7",   "B7",    "C8"};
    private static final double[] FREQUENCIES = { 440.00, 493.88, 523.25, 587.33, 659.25, 698.45, 783.99, 
    	880.00, 987.77, 1046.50, 1174.66, 1318.51, 1396.91, 1567.98, 1760.00, 1975.53,  2093.00, 2349.32, 
    	2637.02, 2793.83, 3135.96, 3520.00, 3951.07, 4186.01 };
 


    public FFT(){
    	initComponents();
     	InputStream in = getClass().getResourceAsStream("musicscore.txt");
        try {
            textArea.read(new InputStreamReader(in), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
         textArea.setHighlighter(hilit);
         
         entryBg = entry.getBackground();
  
         
         InputMap im = entry.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
         ActionMap am = entry.getActionMap();
         im.put(KeyStroke.getKeyStroke("ESCAPE"), RESTART_ACTION);
         am.put(RESTART_ACTION, new RestartAction());
    }
    public void initComponents() {
        entry = new JTextField();
        textArea = new JTextArea();
        status = new JLabel();
        jLabel1 = new JLabel();
        frame = new JFrame();
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("MusicScoreMatching");
        entry.setEditable(false);
        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
		textArea.setFont(textArea.getFont().deriveFont(20f));
        jScrollPane1 = new JScrollPane(textArea);

        jLabel1.setText("Your note:");
		font = new Font("Courier",Font.BOLD,26);
		jLabel1.setFont(font);

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        
		//Create a parallel group for the horizontal axis
		ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
	
		//Create a sequential and a parallel groups
		SequentialGroup h1 = layout.createSequentialGroup();
		ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
	
		//Add a container gap to the sequential group h1
		h1.addContainerGap();
		
		//Add a scroll pane and a label to the parallel group h2
		h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
		h2.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
			
		//Create a sequential group h3
		SequentialGroup h3 = layout.createSequentialGroup();
		h3.addComponent(jLabel1);
		h3.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
		h3.addComponent(entry, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);
		 
		//Add the group h3 to the group h2
		h2.addGroup(h3);
		//Add the group h2 to the group h1
		h1.addGroup(h2);

		h1.addContainerGap();
		
		//Add the group h1 to the hGroup
		hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
		//Create the horizontal group
		layout.setHorizontalGroup(hGroup);
		
			
		//Create a parallel group for the vertical axis
		ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		//Create a sequential group v1
		SequentialGroup v1 = layout.createSequentialGroup();
		//Add a container gap to the sequential group v1
		v1.addContainerGap();
		//Create a parallel group v2
		ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		v2.addComponent(jLabel1);
		v2.addComponent(entry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		//Add the group v2 tp the group v1
		v1.addGroup(v2);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE);
		v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
		v1.addComponent(status);
		v1.addContainerGap();
		
		//Add the group v1 to the group vGroup
		vGroup.addGroup(v1);
		//Create the vertical group
		
		layout.setVerticalGroup(vGroup);
		frame.pack();
		frame.setVisible(true);
	}
    private static double normaliseFreq(double hz) {
        // get hz into a standard range to make things easier to deal with
        while ( hz < 82.41 ) {
            hz = 2*hz;
        }
        while ( hz > 164.81 ) {
            hz = 0.5*hz;
        }
        return hz;
    }
    //closenote
    private static int closestNote(double hz) {
        double minDist = Double.MAX_VALUE;
        int minFreq = -1;
        for ( int i = 0; i < FREQUENCIES.length; i++ ) {
            double dist = Math.abs(FREQUENCIES[i]-hz);
            if ( dist < minDist ) {
                minDist=dist;
                minFreq=i;
            }
        }
        
        return minFreq;
    }
    // compute the FFT of x[], assuming its length is a power of 2
    public static Complex[] fft(Complex[] x) {
        int n = x.length;  

        // base case
        if (n == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (n % 2 != 0) { throw new RuntimeException("n is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[n/2];
        for (int k = 0; k < n/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < n/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + n/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }
	
    // compute the inverse FFT of x[], assuming its length is a power of 2
    public static Complex[] ifft(Complex[] x) {
        int n = x.length;
        Complex[] y = new Complex[n];

        // take conjugate
        for (int i = 0; i < n; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < n; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by n
        for (int i = 0; i < n; i++) {
            y[i] = y[i].scale(1.0 / n);
        }

        return y;

    }

    // compute the circular convolution of x and y
    public static Complex[] cconvolve(Complex[] x, Complex[] y) {

        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.length != y.length) { throw new RuntimeException("Dimensions don't agree"); }

        int n = x.length;

        // compute FFT of each sequence
        Complex[] a = fft(x);
        Complex[] b = fft(y);

        // point-wise multiply
        Complex[] c = new Complex[n];
        for (int i = 0; i < n; i++) {
            c[i] = a[i].times(b[i]);
        }

        // compute inverse FFT
        return ifft(c);
    }


    // compute the linear convolution of x and y
    public static Complex[] convolve(Complex[] x, Complex[] y) {
        Complex ZERO = new Complex(0, 0);

        Complex[] a = new Complex[2*x.length];
        for (int i = 0;        i <   x.length; i++) a[i] = x[i];
        for (int i = x.length; i < 2*x.length; i++) a[i] = ZERO;

        Complex[] b = new Complex[2*y.length];
        for (int i = 0;        i <   y.length; i++) b[i] = y[i];
        for (int i = y.length; i < 2*y.length; i++) b[i] = ZERO;

        return cconvolve(a, b);
    }

    
    
    
    // display an array of Complex numbers to standard output
   /* public static void show(Complex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
        System.out.println();
    }*/
	// display an array of Complex numbers to standard output
    public static String FinalNote(Complex[] x,double pretemp, double prefrequency) {
        
		double temp = 0;
		
		double frequency = 0;
        for (int i = 0; i < x.length/2; i++) {
			if(x[i].norm() > temp){
				temp = x[i].norm();
				position = i;
			}
		}
		/*for (int i = 0; i < x.length/2; i++) {
            //System.out.printf("%d\t%.2f%n",i, x[i].norm()>temp*2/3?x[i].norm():0);
        }*/
		Resolution = SampleFrequency/Sample;
		//System.out.println("highest pos: "+position);
		frequency = position*Resolution;
		if(temp < FFT.AmplitudeBar){
			return null;
		}else if(temp > pretemp){
			/*for (int i = 0; i < x.length/2; i++) {
				if(x[i].norm() > temp/10 || (i<45&&i>39) ||(i<95&&i>80)){
					System.out.printf("%-4d\t%.2f\t%.2f%n", i, i*Resolution, x[i].norm());
				}
			}*/
			//frequency = normaliseFreq(frequency);
            int note = closestNote(frequency);
			//System.out.println("amplitude is nearly: " + temp);
			//System.out.println("Frequency is nearly: " + frequency);
			//System.out.println(note);
            pretemp = temp;
			prefrequency = frequency;
			return NAME[note];
			
			
		
		}else{
			pretemp = temp;
			prefrequency = frequency;
		}
		return null;
        //System.out.println();
    }
    void message(String msg) {
		status.setText(msg);
	}
	public void search(JTextArea textArea, String s) {
		hilit.removeAllHighlights();
			
		//String s = entry.getText();
		if (s.length() <= 0) {
				
			return;
		}
			
		String content = textArea.getText();
		int index = content.indexOf(s, 0);
		if (index >= 0) {   // match found
			try {
				int end = index + s.length();
				hilit.addHighlight(index, end, painter);
				textArea.setCaretPosition(end);
				entry.setBackground(entryBg);
					
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else {
			entry.setBackground(ERROR_COLOR);
			message("Press Esc to restart");
		}
	}

	class RestartAction extends AbstractAction {
		public void actionPerformed(ActionEvent ev) {
			hilit.removeAllHighlights();
			entry.setText("");
			//message("");
			result.setLength(0);
			entry.setBackground(entryBg);
		}
	}
	
    public static void main(String[] args) throws Exception{ 
        //int n = Integer.parseInt(args[0]);
        //Complex[] x = new Complex[n];
    	FFT f = new FFT();
    	//f.initComponents();
    	//f.readFile(textArea);
		//float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        AudioFormat format = new AudioFormat((float)FFT.SampleFrequency, sampleSizeInBits, channels, signed, bigEndian);
		//Constructs an AudioFormat with a linear PCM encoding and the given parameters.
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
		//Constructs a data line's info object from the specified information, 
		//which includes a single audio format.
        TargetDataLine targetDataLine = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
        
        // read about a second at a time
        targetDataLine.open(format, (int)FFT.SampleFrequency);
		//open(AudioFormat format, int bufferSize)
		//Opens the line with the specified format and requested buffer size, causing the line 
		//to acquire any required system resources and become operational
        targetDataLine.start();
        
		int rawLen = (int)FFT.Sample;
		int repeat = 1;
		//FFT.Sample = rawLen*repeat;
        byte[] buffer = new byte[2*rawLen];
        Complex[] a = new Complex[rawLen];
		Complex[] b = new Complex[rawLen];
		//System.out.println(buffer.length);
		
		
		
        int n = -1;

        while((n = targetDataLine.read(buffer, 0, buffer.length)) > 0)  {
			//read(byte[] b, int off, int len)
			//Reads audio data from the data line's input buffer
			//the number of bytes actually read
            for ( int i = 0; i < n; i+=2 ) {
                // convert two bytes into single value
                int value = (short)((buffer[i]&0xFF) | ((buffer[i+1]&0xFF) << 8));
                a[i >> 1] = new Complex(value,0);
            }
			//System.out.println(a.length);// -- 8192
			
			
            
			for(int i =0; i<repeat; i++){
				System.arraycopy(a,0,b,i*a.length,a.length);
			}
			
			Complex[] y = fft(b); 
			
			String s =FinalNote(y,FFT.pretemp,FFT.prefrequency);
			
			if(s != null && !s.equals(pres)){
				result.append(s);
				pres = s;
				System.out.println(result);
				entry.setText(result.toString());
				f.search(textArea,result.toString());
			}
			
			
			try { 
				Thread.sleep(10);
			}catch( Exception e ){}
			
			
					//Turn off metal's use of bold fonts
					//UIManager.put("swing.boldMetal", Boolean.TRUE);
			
			
		}
		
		
    }

}
