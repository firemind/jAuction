package server;

import java.util.Observable;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import java.util.HashMap;

public class MutationStore extends Observable{

	private ArrayList<Mutation> mutations = new ArrayList();
	private int next = 0;		// Index of last element
	private long id_counter = 0;
	private static int maxMutations = 2000;  // Number after which Ringpuffer resets
	
   protected void addMutation(String name, JSONObject data){
	   Mutation mutation = new Mutation(name, data, id_counter++);
	   if(mutations.size() == maxMutations){
		   next = 0; 
	   }
	   mutations.add(next++, mutation);
	   setChanged();
	   notifyObservers(mutation);
   }
   
   private static  int getTrueIndex(int i){
	   if (i < 0){
		   return  maxMutations - i;
	   }else{
		   return i;
	   }
   }

   protected ArrayList<Mutation> getMutations(long last_mutation){
	   int offset = (int) (id_counter - last_mutation);
	   ArrayList<Mutation> returnMutations = new ArrayList();
	   if(offset > 0){
		   int start = next - offset;
		   while(start < (next-1)){
			   returnMutations.add(mutations.get(getTrueIndex(++start)));
		   }
	   }
	   return returnMutations;
   }
   class Mutation {
	 private long id;
	 private JSONObject json;
	 Mutation(String name, JSONObject data, long id){
		   HashMap message = new HashMap();
		   message.put("command", name);
		   message.put("data", data);
		   message.put("mutation_id", id);
		   json = JSONObject.fromObject( message); 
	 }
	 protected JSONObject getJson(){
		 return this.json;
	 }
	 protected long getId(){
		 return this.id;
	 }
	 
   }
}
