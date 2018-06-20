I didn't create a batch file. I use the IDE for testing, since one of the program feature is that many clients are
allowed. I can use IDE to open many client, but if I do it in the command line, I need to open many console panel. So
I keep most testing through the IDE.

There are also some logs in the console, but the console log is only for my personal testing.

The protocol is in the Protocol.docx file


Unfinished work:
    The quit function works, but there is an error exception that I don't have time to figure out how to solve it, so
    I catch it to avoid the "error words". Overall, the quit works as expected.
    The idea quit process is click the "Quit" button to disconnect, then click the "X" to close the window, I didn't
    have time to check what if the client directly click the "X" button to close the window before the socket close.


Extra features:
("viewer", ""attendants" and "players" are all clients, they just describe their role in the game)

    Sounds effect. When players play a move, play a invalid move and win the game, there are sounds effect for them.

    Chat session. Everyone in the game can talk through the chat part.

    No limit number on client side. Besides 2 players play the game, all the other attendants are viewers.

    If there is a new client connects in the mid of a game, the client will receive all the current game data. So the
    progress of the game and players info are displayed for the new attendants.

    After one game is over, all the attendants can apply for playing game via clicking "New Game". First clicks, first
    serves. After 2 attendants click "New Game", the new game will starts between them. All the other attendants can
    only watch them play and chat.

    display the info of the current game players and the list of the attendants in the room.



