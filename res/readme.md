### About/Overview:
<p>
&nbsp &nbsp &nbsp &nbsp The adventure game takes a dungeon, a labyrinth of caves filled with various riches, with your 
ultimate goal of making your way through the dungeon, collecting its riches, and reaching its end. 
As a player, you will be dropped at a random location in the dungeon to start with only knowledge 
of your cave. Each cave possibly has treasure and can go 1- 4 directions North, South, East, or West. 
Every dungeon will have at least 1 one to solve it, but the number of ways to solve it can vary.
However, along with traveling gathering treasure, you may run into a cave-dwelling monster called 
an Otyugh. They are solitary smelly creatures that are found throughout the dungeon. You can 
guarantee one will be at the end of it. Since they are so stinky, their smell can waft through the 
caves and affect their surrounding caves’ smell depending on how close they are and how many of 
them there are. Lucky for you, you are equipped with a bow and Crooked arrows. To start, you have 
three arrows, but you can find more traveling throughout the dungeon. The unique thing about 
these arrows is they will travel freely through tunnels no matter what. If you can hit your mark
enough times, you can kill the Otyugh. Only when they’re dead can you feel safe. You have no chance
of escape when you are uninjured by arrows and will die immediately. If they are injured, you only
have a 50% chance to live when entering a cave with one. If a player fails to make it to the end or
wishes to play again on the same dungeon, they can restart the same game. Or, if they are looking
for a new challenge, they can create a new game with new settings
</p>
<p>
&nbsp &nbsp &nbsp &nbsp The adventure Games goal is to have you solve the dungeon and make it through the dungeon safely
and collect as much as you can along the way. So, we have our dungeon model to represent all the 
inner workings of the dungeon. Then to give the game a more interactive feel, there is a GUI of the
dungeon that allows all the interactions you would want as a player of the game. While the player
travels through the dungeon, the GUI will update the dungeon map the player builds. While also 
keeping track of all the collected treasures throughout their travels in the caves. We can also 
interact with a console-level by asking for various cmds you can do in the dungeon, such as shoot,
move and pick up. As the state of the dungeon updates, you will hope to solve it and make it out 
safely.

</p>

### List of Features:
<ul> 
    <li>
    A dungeon is created with a specified width and height.
    </li>
    <li>
    A dungeon always creates one where they start, and the end is at least five moves apart.
    </li>
    A dungeon is created with specified interconnectivity.
    <li>
    A dungeon can wrap, meaning if a player is at (0,0) in a wrapping dungeon and moves north, they could go to (0, height -1).
    </li>
    <li>
    A dungeon will hold treasure.
    </li>
<li>	A player can pick up treasure along the way.
</li>
<li>	They are not required to pick up treasure in a given cave.
</li>
<li>	There is no limit to how much treasure a player can hold.
</li>
<li>	There is no limit to how much a given cave can hold for treasure.
</li>
<li>	The dungeon can provide a deception at any given time. Which provides players treasure, the cave’s treasure, and the directions they can travel. The smell and if it’s a tunnel or not and if there is a dead monster in it.
</li>
<li>	A cave can hold diamonds, rubies, and sapphires.
</li>
<li>	A cave can have directions north, south, east, or west.
</li>
<li>	If the player reaches the end, they are still allowed to travel around the dungeon more.
</li>
<li>	A 2d grid represents the dungeon.
</li>
<li>	A player will always be dropped at the start of the dungeon.
</li>
<li>	A cave can have 1-4 connections.
</li>
<li>	A cave with two connections is considered a tunnel.
</li>
<li>	Crooked Arrows can be found through the dungeon at the same rate as treasure.
</li>
<li>	Arrows can be found in both caves and tunnels.
</li>
<li>	Players will start with three arrows.
</li>
<li>	In the dungeon, you can find a specified number of Otyughs.
</li>
<li>	There is always an Otyugh in the end cave.
</li>
<li>	Otyughs take two shots to kill.
</li>
<li>    If an Otyughs has been shot and you run into it, you have 50% to escape.
</li>
<li>	If you run into an uninjured Otyughs, you will die immediately.
</li>
<li>	Otyughs are only found in non-Tunnel Caves.
</li>
<li>	There is a slight smell if there is one Otyughs 2 moves away.
</li>
<li>	There is a pungent smell if there are more than one Otyughs two moves away.
</li>
<li>	If you are one move away from an Otyughs, there is a pungent smell.
</li>
<li>	If there are no Otyughs within two moves of you, there is no smell.
</li>
<li>	You can shoot an arrow at a distance of 5.
</li>
<li>	Arrows travel freely through tunnels, and they do not count as distances traveled.
</li>
<li>	Suppose an arrow goes into a tunnel in the east. In the tunnel’s exit in the south, the hand will go out the south and continue along that direction. Similar for all other variations of this.
</li>
<li>	Arrows will be stopped if they are in a non-tunnel with no cave exit in the direction the arrow is traveling.
</li>
<li>	The arrows must be exact when making contact with Otyughs. If one is a distance of 1 away and you shoot two, it will not hit its mark.
</li>
<li>	The ability to restart the dungeon from the original state of the game
</li>
<li>	The ability to create a new dungeon game with new settings within the GUI window
</li>
<li>	A graphical display of the dungeon
</li>
<li>	The ability to map the dungeon graphically to keep track of places traveled
</li>
<li>	The graphical display keeps track of the player’s location.
</li>
<li>	The display allows the user to see all items they carry
</li>
<li>	The display tells us cave contents plus other info about the cave that is essential.
</li>
<li>	The ability to move with arrow keys or by click
</li>
<li>	The Dungeon map is scrollable.
</li>
<li>	The GUI displays an update message about each action taken.
</li>
<li>	The GUI updates after each action taken
</li>
</ul>

### How To Run:
Run the jar file will just run if you use java -jar (File path)
If you wish to run normally with the GUI, do not pass any arguments other than the file path.

If you want to Run in Console Text Mode, you Must Give Arguments below:
There is a Total of 6 Arguments you must provide in the order described below
<ol>

<li>
Arg1: Width of the dungeon
      <ul>
        <li>
        Must be greater than or equal to 6
        </li>
    </ul>
</li>


<li>
Arg2: Height of dungeon
     <ul>
        <li>
        Must be greater than or equal to 6
        </li>
    </ul>
</li>

<li>
Arg3: Interconnectivity of the dungeon greater than or equal to 0
    <ul>
        <li>
        In the case of a wrapping dungeon max value is (W x H) + 1
        </li>
        <li>
        In the case of a Non-Wrapping Dungeon, the max value is (W – 1) x 
        </li>
    </ul>
</li>

<li>
Arg4: Wrapping or not
    <ul>
        <li>
        true if wrapping
        </li>
        <li>
         false if not wrapping 
        </li>
    </ul>

</li>

<li>
Arg5: Percentage of caves that will hold treasure/arrows as integer
    <ul>
        <li>
        0-100
        </li>
    </ul>

</li>

<li>
Arg6: Number of monsters as integer should be 20% or less of total cave count 
    <ul>
        <li>
        Ex: 6x6 has 36 total caves is a max of 7 monsters
        </li>
    </ul>

</li>

</ol>

### How to Use the Program GUI:
**<ins>Main Screen</ins>**
<ul>
    <li>
        <b><ins> Hitting the key "p" </ins> </b>
        <ul>
            <li>
            It picks up whatever is in the current cave you are in.
            </li>
        </ul>
    </li>
    <li>
        <b><ins> Hitting an Arrow key </ins> </b>
        <ul>
            <li>
                <b><ins> If a number was Hit Before </ins> </b>
                <ul>
                     <li>
                        If Up arrow Key shoot North and shoot the arrow the distance of the number that was hit priror
                    </li>
                    <li>
                        If Down arrow Key shoot South and shoot the arrow the distance of the number that was hit priror
                    </li>
                    <li>
                        If Left arrow Key shoot West and shoot the arrow the distance of the number that was hit priror
                    </li>
                    <li>
                        If Right arrow Key shoot East and shoot the arrow the distance of the number that was hit priror
                    </li>
                </ul>
            </li>
            <li>
                <b><ins> If no number was hit before </ins> </b>
                <ul>
                    <li>
                         If Up arrow Key Move North
                    </li>
                     <li>
                         If Down arrow Key Move South
                    </li>
                     <li>
                         If Left arrow Key Move West
                    </li>
                     <li>
                         If Right arrow Key Move East
                    </li>
                </ul>
            </li>
        </ul>
    </li>
    <li>
    <b><ins> Clicking a Cave Cell on the window </ins> </b>
        <ul>
        If the cell is directly adjacent to the current player’s location and there is a direct 
        pathway connecting them (aka one move) It will move the player to that cell.
        </ul>
    </li>
    <li>
        <b><ins> JMenu Items </ins> </b>
        <ul>
            <li>
                <b><ins> Quit </ins> </b>
                <ul>
                    Quits the Game
                </ul>
            </li>
            <li>
                <b><ins> Restart </ins> </b>
                 <ul>
                    Restarts the game and resets the current dungeon such that everything is the 
                    same, that being start, end, monster locations, treasure counts, etc. The current
                    dungeon’s initial starting state was not the current state it was in when a restart 
                    was called. 
                </ul>
            </li>
            <li>
                <b><ins> New Game </ins> </b>
                 <ul>
                    It opens a window where you can set all the dungeon stats for your new dungeon, 
                    and two buttons create an exit. 
                    The exit will get you out of the window 
                    The Create will make that dungeon and set the window to that dungeon.
                </ul>
            </li>
        </ul>
    </li>
    <li>
        <b><ins> End of Game Sequence </ins> </b>
        <ul>
At the end of the game, a window will popup and allow for a restart, create a new Game with new settings or quit the game.
        </ul>
    </li>
</ul>

### How to Use the Program Text Console:
<ul>
    <li>
        <b><ins> Enter M to Move </ins> </b>
        <ul>
            In a move, you can Enter: <b> “N, S, E, W” </b> are your four options
            A message will be sent if you enter a move that does not meet those four options or
            makes it, so the player does not progress. A message will also appear if the play
            escapes an Otyugh.
        </ul>
    </li>
    <li>
        <b><ins> Enter: “P” to Pickup </ins> </b>
        <ul>
            If there are items to pick up in the dungeon, a message will tell you what you picked up.
            If there are no items to pick up, it will tell you there are no items to pick up and 
            ask for a new cmd.
        </ul>
    </li>
    <li>
        <b><ins> Enter: “S” to Shoot: </ins> </b>
        <ul>
            Shoot will require you to Enter a Integer value for the distance of the shot And to 
            one “N, S, E, W” are your four options If you fail to give an integer an or a not a move,
            you will get a message If you fail to enter valid directions or a valid distance,
            you will get a message from the error that would have been thrown
        </ul>
    </li>
     <li>
        <b><ins> Enter: “Q or q” to Quit: </ins> </b>
        <ul>
            If you are not already within a cmd S or M. If you enter q, it will quit the game for you.
            If you are at the end of the dungeon and enter q or Q, it will note that you won.
        </ul>
    </li>
</ul>

### Runs:
<ul>
    <li>
        I ran the program with a dungeon that was 15 x 20, 8 interconnectivity, wrapped, had 30% 
        item spread, and two monsters within.
    </li>
    <li>
        I traversed the dungeon and its caves throughout using key clicks and mouse clicks to move 
        the player.
    </li>
    <li>
        When I came across items in a cave or tunnel, I picked them up using my key click.
    </li>
    <li>
        When I came across a strong smell, I shot arrows to figure out where the monster was by looking 
        at messages sent back on the view about the arrow shot.
    </li>
     <li>
        I would kill the subsequent monster I came close to.
    </li>
     <li>
        Reached the end 
    </li>
     <li>
        I opened up the new game window from the end game window that popped up when 
        reaching the end of the dungeon.
    </li>
     <li>
        I chose to quit the game. 
    </li>
</ul>

### Design/Model Changes:
<b><ins> Players </ins> </b>
<p>
&nbsp &nbsp &nbsp &nbsp The one slight change I made to the player class was resetting the player to a location and 
starting ideas of being alive and having three arrows and no other items.  

</p>

<b><ins> Dungeon Model </ins> </b>
<p>
&nbsp &nbsp &nbsp &nbsp At the dungeon level, I had the read-only version of the dungeon but found I needed to add 
some getters to the Dungeon interface so the read-only could give out certain pieces of info. I also
added a field of the initial state of the dungeon to the DungeonImpl, so I had a way to have a deep 
copy of the dungeon state at the start of the game, so when it comes to resetting the model, there are 
no issues. Continuing on the idea of resetting, the dungeon reset method was added on as well.
</p>

<b><ins> Controller/Features </ins> </b>
<p>
  &nbsp &nbsp &nbsp &nbsp At the Controller level for my features class, I condensed my move and shoot functions into one for
each one. I also changed the return type of my functions to return a string. This makes it, so 
the view gets some feedback about the action taken and an error with some entered info. The gave 
view access to the error message but not the error itself. I also added a new game into features that
take the arguments required for a new dungeon model. It then resets all the necessary parts in the 
controller and sends info to the view about the new model used.
</p>

<b><ins> View </ins> </b>
<p>
  &nbsp &nbsp &nbsp &nbsp At the level of my iView interface, nothing changed other than the addition of the ability to reset
the read-only model to a new model. This method is called when a new game is created.
</p>
<p>
  &nbsp &nbsp &nbsp &nbsp The significant changes come at the class level, so I found the need for a few added panels to be 
kept as fields in my class and a few other pieces of info for my view implementation. I
initially thought I would only need one panel, and the updates would be done at the repaint level,
but I found that not to work.
</p>
<p>
  &nbsp &nbsp &nbsp &nbsp I split up the ideas I wanted to present into four separate parts—the GamePanel, which represents
the board, or the dungeon's map. The DescriptionPanel handles all of the info about the current cave 
you are in and the player's current state regarding items they carry. Then I have an EndPanel, 
a window that will pop up at the end of the game and provide the option to the player on what they 
wish to do. Then finally, I have my GameMenu, which is the JMenu attached to the game, 
and this builds all the things the menu might lead to as well. Each of these Panels had getters for 
the info they contained attached to specific actions set in the Main View implantation. 
</p>

### Assumptions:
<ul>
    <li>	
        That a dungeon has a minimum of 6 x 6 for the setup.
    </li>
    <li>	
        A tunnel can’t hold treasure. 
    </li>
    <li>
        That the start and finish are at least five apart.
    </li>
    <li>
        A player is not required to pick up treasure. 
    </li>
    <li>
        There is no Max amount of treasure a player can pick up. 
    </li>
    <li>
        If the player reaches the end, they are not required to finish the dungeon. 
    </li>
    <li>
        There are only three types of treasure. 
    </li>
    <li>
        There are only four directions to travel. 
    </li>
    <li>
        There is at least one path from every cave to every other cave. 
    </li>
    <li>
        There is always at least one Otyugh in the dungeon, in the end, cave. 
    </li>
    <li>
        If the player enters a cave with an injured Otyugh and escapes, they return to the
        cave they were previously at. 
    </li>
    <li>
        The number of Otyugh is less than or equal to 20% of a total node in the dungeon. 
    </li>
    <li>
        If the Otyugh is dead, it does not let off any smell. 
    </li>
    <li>
        Arrows travel freely through tunnels. 
    </li>
    <li>	
        If an arrow makes it into a, it has an Otyugh, and the arrow cannot continue due to connection
        restrictions. Even if it has more to travel, it will hit the Otyugh since there is nowhere else
        for it to go. 
    </li>
    <li>    
        Once you shoot an arrow, you cannot just go and find it in the cave. 
    </li>
    <li>
        The arrow shot is recognized by hitting a number before hitting an arrow. 
    </li>
    <li>    
        That the user wants the Current caves info plus their always-on info display 
    </li>
    <li>
        Once a player leaves a particular cave, they do not know the info of that cave until 
        they are within it again.   
    </li>
</ul>

###Limitations:
<ul>
    <li>
        If someone wanted a smaller dungeon, that is not possible. 
    </li>
    <li>
        There are always only three types of treasure due to it being an enum. 
    </li>
    <li>
        There are always going to be four types of directions due to being an enum. 
    </li>
    <li>
        A player can’t keep track of where they have been. 
    </li>
    <li>
        Once a cave has one of its directions set, it cannot be reset. 
    </li>
    <li>
        If you don’t designate a high enough percent of arrows and too many monsters, the dungeon may be impossible to solve. 
    </li>
    <li>
        There is no way of knowing you killed an Otyugh until you enter a cave with a dead one. 
    </li>
    <li>
        The number of monsters is finite. 
    </li>
    <li>
        Since smell is an Enum, you can’t just add a new smell. 
    </li>
    <li>
        The only way to quit the dungeon and collect everything is to win. 
    </li>
    <li>
        You cannot pick up items in a cave one by one. It is an all-in-one-go situation. 
    </li>
    <li>
        Shooting requires two key hits concurrently\ 
    </li>
    <li> 
        The player only maps the dungeon, not the contents of the caves, so once they leave a cave, they do not keep track of the contents.
    </li>
</ul>

###Citations:  
- For a reference I looked through multiple tutorials on the Oracle website for Swing
  https://docs.oracle.com/javase/tutorial/uiswing/


