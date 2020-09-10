import javafx.util.Pair;

public class Narrator {
    int attitude = 0;
    String[] questions = {"what","who","when", "how","why", "does",
     "has","can", "shall", "is", "which", "did", "where"};
    String[] movement = {"move", "walk"}; 
    String[] travel = {"travel","go"};
    String[] greetings = {"hi","hello","morning","goodmorning", "sup","whatsup","what'sup", "hey","yo"};
    String[] attack = {"hit", "attack", "beat"};
    String newLoc = "";


    public Pair<String,Integer> think(String s){
        
        String[] words = s.split("\\W+");
        System.out.println(words[0]);
        if(words.length >= 1){
        for(int i = 0; i < questions.length; i++){
            if(words[0].toLowerCase().equals(questions[i].toLowerCase())){ 
                return new Pair<>("How am i supposed to know?", 0);
            }
        }
        for(int i = 0; i<greetings.length; i++){
            if(words.length>1 &&((words[0].toLowerCase().concat(words[1])).equals(greetings[i].toLowerCase()))){
                return new Pair<>("Do you really expect me to answer?", 0);
            }
            else if(words[0].toLowerCase().equals(greetings[i].toLowerCase())){
                return new Pair<>("Leave me alone.", 0);
            }
        }
        for(int i = 0; i < attack.length; i++){
            if(words[0].toLowerCase().equals(attack[i].toLowerCase())){
                return new Pair<>("damage done!",0);
            }
        }
        for(int i = 0; i < movement.length; i++){
            if(words[0].toLowerCase().equals(movement[i].toLowerCase())){
                return new Pair<>("Okay ",1);
            }
        }
        for(int i = 0; i < travel.length; i++){
            if(words[0].toLowerCase().equals(travel[i].toLowerCase())&& words.length > 1){
                newLoc = words[1];
                return new Pair<>("You are going to go to "+words[1],2);
            }
        }
         return new Pair<>("Idk what you want like stop being annoying", 0);
    }
    else return new Pair<>("Dont be silly",0);
}
    public String getNewLoc(){
        return newLoc;
    }
    
}