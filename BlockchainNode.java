class BlockchainNode { // The basic BlockchainNode...
	String currHash;  // To store the hash
	String contents;  // To store the data
	int blockNumber;  // The number of the node in the block after the Genesis Node
	BlockchainNode next; // The next BlockchainNode
	BlockchainNode prev; // The previous BlockchainNode

	BlockchainNode (String s) {
		// Creates a Genesis node
		currHash= StringUtil.applySha256(Integer.toString(0)+"AllZeros:This is the Genesis String!"+s);
		contents= s;
		blockNumber= 0;
		next= null;
		prev= null;
	}	

	BlockchainNode (String s, String pH, int bN) {
		// Creates a new blocknumbered bN with contents s	
		currHash= StringUtil.applySha256(Integer.toString(bN)+pH+s);
		contents= s;
		blockNumber= bN;
		next= null;	
		prev= null;
	}
}

class SimpleBlockchain {
	// Class invariant: all nodes in the Blockchain satisfy "Blockchain Validity"
	// i.e.  Blockchain Validity holds of all nodes (except the Genesis node):
	// StringUtil.applySha256(this.blockNumber+ prev.currHash+this.contents == this.currHash)
	// AND the Genesis node has been correctly initialised
	BlockchainNode genesisNode; // Created by calling BlockchainNode (String s)
	BlockchainNode lastNode;   // The last node in the Blockchain

	SimpleBlockchain(String s){
		genesisNode= new BlockchainNode(s);
		lastNode= genesisNode;
	}


	void addBlock(String s) { // 
		// Post: Creates a new valid block with contents s, and adds it
		// to the current SimpleBlockchain so that the result satisfies the blockchain condition.
		BlockchainNode newNode = new BlockchainNode(s,lastNode.currHash,lastNode.blockNumber+1);
		lastNode.next = newNode;
		newNode.prev=lastNode;
		lastNode=newNode;
		
	}

	boolean validate () { // 
		// Post: Returns true if the SimpleBlockchain is valid, i.e. satisfies the blockChain condition
		// null SimplBlockchains are valid
		if(genesisNode.currHash.compareTo(StringUtil.applySha256(Integer.toString(0)+"AllZeros:This is the Genesis String!"+genesisNode.contents))!=0)
			return false;
		BlockchainNode currNode = genesisNode.next;
		while(currNode!=null) {
			if(currNode.currHash.compareTo(StringUtil.applySha256(Integer.toString(currNode.blockNumber)+currNode.prev.currHash+currNode.contents))!=0)
				return false;	
		currNode=currNode.next;
		}
		return true;
	}	

	BlockchainNode findTamperedNode() { // 
		// Post: If validate returns false, returns the address of the first node which fails to validate
		// If validate returns true, then returns null
			if(genesisNode.currHash.compareTo(StringUtil.applySha256(Integer.toString(0)+"AllZeros:This is the Genesis String!"+genesisNode.contents))!=0)
				return genesisNode;
			BlockchainNode currNode = genesisNode.next;
			while(currNode!=null) {
				if(currNode.currHash.compareTo(StringUtil.applySha256(Integer.toString(currNode.blockNumber)+currNode.prev.currHash+currNode.contents))!=0)
					return currNode;	
			currNode=currNode.next;
			
		}
			return null;
	}

} 


class StringUtil {
	//Applies Sha256 to a string and returns the result. 
	public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}	
}
