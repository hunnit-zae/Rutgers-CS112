package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);

	/* Your code goes here */
        sortedCharFreqList = new ArrayList<CharFreq>();
        int n = 0;
        int[] count = new int[128];

        while(StdIn.hasNextChar() == true)
        {
            char character = (char)((int)StdIn.readChar());
            count[character] += 1;
            n++;
        }
        for(int i = 0; i < count.length; i++)
        {
            if(count[i] > 0)
            {
                sortedCharFreqList.add(new CharFreq((char) i, (double)count[i] / n));
            }
        }
        if(sortedCharFreqList.size() == 1) 
        {
            char c = (char)((int) sortedCharFreqList.get(0).getCharacter() + 1);
            sortedCharFreqList.add(new CharFreq(c, 0));
        }

        Collections.sort(sortedCharFreqList);

        return;
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     * @return 
     */
    public void makeTree() {

	/* Your code goes here */
    Queue <TreeNode> target = new Queue<TreeNode>();
    Queue <TreeNode> dequeue = new Queue<TreeNode>();
    Queue <TreeNode> source = new Queue<TreeNode>();

    for(int i = 0; i < sortedCharFreqList.size(); i++)
        {
            TreeNode root = new TreeNode();
            root.setData(sortedCharFreqList.get(i));
            source.enqueue(root);
        }
            TreeNode a = new TreeNode();
            TreeNode small1 = new TreeNode();
            TreeNode small2 = new TreeNode();
            double probOcc1;
            double probOcc2;

            while(!source.isEmpty() || target.size()!= 1)
            {
                while(dequeue.size()<2)
                {
                    if(target.isEmpty())
                    {
                        a = source.peek();
                        source.dequeue();
                        dequeue.enqueue(a);
                    }
                    else
                    {
                        if(!source.isEmpty())
                            {
                                if(target.peek().getData().getProbOcc() >= source.peek().getData().getProbOcc())
                                {
                                    a = source.peek();
                                    source.dequeue();
                                    dequeue.enqueue(a);
                                }
                                else
                                {
                                    a = target.peek();
                                    target.dequeue();
                                    dequeue.enqueue(a);
                                }
                            }
                        else
                        {
                            a = target.peek();
                            target.dequeue();
                            dequeue.enqueue(a);
                        }
                    }       
                }
            if(dequeue.isEmpty())
            {
                small1 = null;
            }
            else
            {
                small1 = dequeue.peek();
                dequeue.dequeue();
            }
            if(dequeue.isEmpty())
            {
                small2 = null;
            }
            else
            {
                small2 = dequeue.peek();
                dequeue.dequeue();
            }
            if(small1 == null)
            {
                probOcc1 = 0;
            }
            else
            {
                probOcc1 = small1.getData().getProbOcc();
            }
            if(small2 == null)
            {
                probOcc2 = 0;
            }
            else
            {
                probOcc2 = small2.getData().getProbOcc();
            }

            TreeNode tree = new TreeNode(new CharFreq(null, probOcc1+probOcc2),small1,small2);
            target.enqueue(tree);
        }
        huffmanRoot = target.dequeue();
    }


    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array small null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

	/* Your code goes here */
    encodings = new String[128];
    helper(encodings, huffmanRoot.getLeft(), "0");
    helper(encodings, huffmanRoot.getRight(), "1");
}

// helper methods
static void helper(String[] Hroot, TreeNode arr, String path) {
    // base case
    if(arr == null) {
        return;
    }
    
    // add encoding if CharFreq.character is not null
    if (arr.getData().getCharacter() != null) {
        char chr = arr.getData().getCharacter();
        Hroot[chr] = path;
    }
    helper(Hroot, arr.getLeft(), path + "0");
    helper(Hroot, arr.getRight(), path + "1");
}


    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

	/* Your code goes here */
        String bits = "";
        
            while(StdIn.hasNextChar()) 
            {
                char c = StdIn.readChar();
                bits += encodings[c];
            }
            writeBitString(encodedFile, bits);
        }

    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bits The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bits) {
        byte[] bytes = new byte[bits.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bits.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bits = pad + bits;

        // For every bit, add it to the small2 spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bits.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

	/* Your code goes here */
    String bits = readBitString(encodedFile);
        
    TreeNode root = huffmanRoot;

    for (int i = 0; i < bits.length(); i++) 
    {
        char dir = bits.charAt(i);
        
        if (dir == '0') 
        {
            huffmanRoot = huffmanRoot.getLeft();
        } 
        else 
        {
            huffmanRoot = huffmanRoot.getRight();
        }

        if (huffmanRoot.getData().getCharacter() != null) 
        {
            StdOut.print(huffmanRoot.getData().getCharacter());
            huffmanRoot = root;
        }
    }
}


    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bits = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bits = bits + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bits.charAt(i) == '1') return bits.substring(i+1);
            }
            
            return bits.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
