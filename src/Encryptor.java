import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        int k = 0;
        for(int r = 0; r < numRows; r++)
        {
            for(int c = 0; c < numCols; c++)
            {
                if(str.length() < numRows * numCols)
                {
                    if(k < str.length())
                    {
                        letterBlock[r][c] = str.substring(k,k+1);
                        k++;
                    }
                    else
                    {
                        letterBlock[r][c] = "A";
                    }
                }
                else
                {
                    letterBlock[r][c] = str.substring(k,k+1);
                    k++;
                }
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String encrypted = "";
        for(int c = 0; c < numCols; c++)
        {
            for(int r = 0; r < numRows; r++)
            {
                encrypted += letterBlock[r][c];
            }
        }
        return encrypted;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        String encryptedMessage = "";
        while(message != "")
        {
            fillBlock(message);
            encryptedMessage += encryptBlock();
            if(message.length() > numRows * numCols)
            {
                message = message.substring(numRows * numCols);
            }
            else
            {
                message  = "";
            }
        }
        return encryptedMessage;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        String decryptedMessage = "";
        while(encryptedMessage != "")
        {
            int count = 0;
            String[][] arr = new String[numRows][numCols];
            if(encryptedMessage.length() > numCols * numRows)
            {
                for(int c = 0; c < arr[0].length; c++)
                {
                    for(int r = 0; r < arr.length; r++)
                    {
                        arr[r][c] = encryptedMessage.substring(count, count + 1);
                        count++;
                    }
                }
                for(int row = 0; row < arr.length; row++)
                {
                    for(int col = 0; col < arr[row].length; col++)
                    {
                        decryptedMessage += arr[row][col];
                    }
                }
            }
            else
            {
                for(int c = 0; c < arr[0].length; c++)
                {
                    for(int r = 0; r < arr.length; r++)
                    {
                        arr[r][c] = encryptedMessage.substring(count, count + 1);
                        count++;
                    }
                }
                for(int r = arr.length - 1; r >= 0; r--)
                {
                    for(int c = arr[r].length - 1; c >= 0; c--)
                    {
                        if(arr[r][c].equals("A"))
                        {
                            arr[r][c] = null;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                for(int r2 = 0; r2 < arr.length; r2++)
                {
                    for(int c2 = 0; c2 < arr[r2].length; c2++)
                    {
                        if(arr[r2][c2] == null)
                        {
                            break;
                        }
                        else
                        {
                            decryptedMessage += arr[r2][c2];
                        }
                    }
                }
            }
            encryptedMessage = encryptedMessage.substring(numRows * numCols);
        }
        return decryptedMessage;
    }
}