package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

// import javax.swing.tree.TreeNode;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Kanghwi, Lee
 * 
 */
public class HuffmanCoding {
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                System.exit(1);
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
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /**
     * Reads a given text file character by character, and returns an arraylist
     * of CharFreq objects with frequency > 0, sorted by frequency
     * 
     * @param filename The text file to read from
     * @return Arraylist of CharFreq objects, sorted by frequency
     */
    public static ArrayList<CharFreq> makeSortedList(String filename) {
        StdIn.setFile(filename);
        ArrayList<CharFreq> Freq=new ArrayList<>();
        int[] count=new int[128];
        
        int length = 0;
        while(StdIn.hasNextChar()){
            ++length;
            char c = StdIn.readChar();
            count[c]++;
        }

        for(Character i=0; i<count.length; ++i){
            if(count[i]>0){
                Freq.add(new CharFreq(i, (double)count[i]/length));
            }
        }
        if(Freq.size() == 1) {
            int c = (Freq.get(0).getCharacter() + 1);

            if(c > 127) {
                c = 0;
            }
            
            Freq.add(new CharFreq((char)c, 0));
        }

        Collections.sort(Freq);
        return Freq;
    }

    /**
     * Uses a given sorted arraylist of CharFreq objects to build a huffman coding tree
     * 
     * @param sortedList The arraylist of CharFreq objects to build the tree from
     * @return A TreeNode representing the root of the huffman coding tree
     */
    public static TreeNode makeTree(ArrayList<CharFreq> sortedList) {
        Queue<TreeNode> source=new Queue<>();
        Queue<TreeNode> target=new Queue<>();
        for(CharFreq f: sortedList){
            source.enqueue(new TreeNode(f,null,null));
        }
        while(!source.isEmpty() || target.size()>1){
            TreeNode t=null, s = null;
            TreeNode left=null;
            TreeNode right=null;


            t= (!target.isEmpty()) ? target.peek(): null;
            s= (!source.isEmpty()) ? source.peek(): null;

            if(t!=null && (s==null || t.getData().getProbOccurrence()<s.getData().getProbOccurrence())){
                left=target.dequeue();
            }
            else{
                left=source.dequeue();
            }
            t= (!target.isEmpty()) ? target.peek(): null;
            s= (!source.isEmpty()) ? source.peek(): null;
        
            if(t!=null && (s==null || t.getData().getProbOccurrence() < s.getData().getProbOccurrence())){
                right=target.dequeue();
            }
            else{
                right=source.dequeue();
            }
            CharFreq parent=new CharFreq();
            parent.setProbOccurrence(left.getData().getProbOccurrence()+right.getData().getProbOccurrence());
            target.enqueue(new TreeNode(parent,left,right));

        }
        return target.dequeue();
    }


    private static void _makeEncodings(TreeNode root, String[] arr, String path) {
        if(root.getLeft() == null && root.getRight() == null) {
            Character c = root.getData().getCharacter();
            arr[c] = path;
        }

        if(root.getLeft() != null) {
            _makeEncodings(root.getLeft(), arr, path + "0");
        }

        if(root.getRight() != null) {
            _makeEncodings(root.getRight(), arr, path + "1");
        }
    }

    /**
     * Uses a given huffman coding tree to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null
     * 
     * @param root The root of the given huffman coding tree
     * @return Array of strings containing only 1's and 0's representing character encodings
     */
    public static String[] makeEncodings(TreeNode root) {
        String[] output = new String[128];

        _makeEncodings(root, output, "");

        return output;
    }

    /**
     * Using a given string array of encodings, a given text file, and a file name to encode into,
     * this method makes use of the writeBitString method to write the final encoding of 1's and
     * 0's to the encoded file.
     * 
     * @param encodings The array containing binary string encodings for each ASCII character
     * @param textFile The text file which is to be encoded
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public static void encodeFromArray(String[] encodings, String textFile, String encodedFile) {
        StdIn.setFile(textFile);
        
        String bitString = "";
        
    for(int i = 0; i < textFile.length(); i++) {
        while(StdIn.hasNextChar()) {
            Character c = StdIn.readChar();
            bitString += encodings[c];
        }
        writeBitString(encodedFile, bitString);
    }
    
    /**
     * Using a given encoded file name and a huffman coding tree, this method makes use of the 
     * readBitString method to convert the file into a bit string, then decodes the bit string
     * using the tree, and writes it to a file.
     * 
     * @param encodedFile The file which contains the encoded text we want to decode
     * @param root The root of your Huffman Coding tree
     * @param decodedFile The file which you want to decode into
     */
    public static void decode(String encodedFile, TreeNode root, String decodedFile) {
        StdOut.setFile(decodedFile);
        TreeNode node = root;
        String bits = readBitString(encodedFile);

        String output = "";

        for(int i = 0; i < bits.length(); i++) {
            if(node.getLeft() == null && node.getRight() == null) {
                output += node.getData().getCharacter();
                node = root;
            }
            if(bits.charAt(i) == '0') {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        output += node.getData().getCharacter();

        StdOut.print(output);
    }
}


public void makeTree(){
    Queue<TreeNode> src= new Queue<>(), dest = new Queue<TreeNode>();
    for(CharFreq CharFreq: sortedCharFreqList)
        src.enqueue(new TreeNode(CharFreq, null, null));

    TreeNode left, right;
    while (!src.isEmpty()){
        left = dequeue_smallest(src, dest);
        right = dequeue_smallest(src, dest);
        if (left != null && right != null){
            TreeNode combined_node = combine_tree_nodes(left, right);
            dest.enqueue(combined_node);
        }
        else if (left != null)
            dest.enqueue(left);
        else
            dest.enqueue(right);
    }
    while (dest.size() > 1){
        left = dest.dequeue();
        right = dest.dequeue();
        TreeNode combined_node = combine_tree_nodes(left, right);
        dest.enqueue(combined_node);
    }
    huffmanRoot = dest.dequeue();
}