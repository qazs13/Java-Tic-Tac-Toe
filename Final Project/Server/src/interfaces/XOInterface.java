package interfaces;

import java.util.Vector;


public class XOInterface {
    
    private String typeOfOperation = null;
    private Boolean operationResult;
    private Player player = null;
    private Gamelog gamelog = null;
    public Vector<Player> Players= null;
    private int fieldNumber; 
    private char signPlayed;
    private Boolean moveReceived;
            
    public XOInterface()
    {
        Players = new Vector<>();
    }
    
    public XOInterface(String typeOfOperation)
    {
        this.typeOfOperation = typeOfOperation;
    }
    
    public XOInterface(String typeOfOperation,Player player)
    {
        this.typeOfOperation = typeOfOperation;        
        this.player = player;
    }
    public XOInterface(String typeOfOperation,Gamelog gamelog)
    {
        this.typeOfOperation = typeOfOperation;        
        this.gamelog = gamelog;
    }
    public XOInterface(String typeOfOperation,Gamelog gamelog,int fieldNumber,char signPlayed)
    {
        this.typeOfOperation = typeOfOperation;        
        this.gamelog = gamelog;
        this.fieldNumber=fieldNumber;
        this.signPlayed=signPlayed;
    }
    public XOInterface(String typeOfOperation,Player player,Gamelog gamelog)
    {
        this.typeOfOperation = typeOfOperation;        
        this.player = player;
        this.gamelog = gamelog;
    }
    
    public void setTypeOfOpearation(String typeOfOperation)
    {
        this.typeOfOperation = typeOfOperation;
    }    
    
    public String getTypeOfOpearation()
    {
        return typeOfOperation;
    }
    
    public void setOpearationResult(boolean operationResult)
    {
        this.operationResult = operationResult;
    }    
    
    public Boolean getOpearationResult()
    {
        return operationResult;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public Gamelog getGameLog()
    {
        return gamelog;
    }    
    
    public void setFieldNumber (int fieldNumber)
    {
        this.fieldNumber = fieldNumber;
    }
    
    public void setSignPlayed (char signPlayed)
    {
        this.signPlayed = signPlayed;
    }    
    
    public int getFieldNumber ()
    {
        return fieldNumber;
    }
    
    public char getSignPlayed ()
    {
        return signPlayed;
    }        
}
