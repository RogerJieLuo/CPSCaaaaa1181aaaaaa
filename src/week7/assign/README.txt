After the image displayed, these are the actions allowed:
    Keyboard (not from number pad):
        "1", eyebrow twitch
        "2", ear flap
        "3", nose twitch
        "4", eye blink
        "5", change smile
        "6", change hair size
        "R", reset pupils positions
        "LEFT" arrow key: it will start eyebrow twitching if it's not twitching.
                          And it will increase the speed of eyebrow twitching if
                          you keep pressing it or just hold it. You can stop them
                          by pressing "1".
        "RIGHT" arrow key: it will start eyebrow twitching if it's not twitching.
                          And it will decrease the speed of eyebrow twitching if
                          you keep pressing it or just hold it. You can stop them
                          by pressing "1".
        "UP" arrow key: it will start hair change action if it's not changing.
                      And it will increase the speed of hair changing. You may
                      need to hold "UP" for a while to see the speed up. You can
                      stop it by pressing "6";

        "DOWN" arrow key: it will start hair change action if it's not changing.
                         And it will decrease the speed of hair changing. You may
                         need to hold "UP" for a while to see it slows down. You can
                         stop it by pressing "6";

    Mouse click:
        The pupil will look towards to the position where mouse released. The click
        action works even while the eyes are blinking, but will see a little weird
        thing which will be declared at the end of README.txt.
        Pupil positions will be reset when press "R" or resize the window.


Note:
    The actions corresponding to the numbers can be stopped by pressing the same
    number again.
    For the mouse click action, I use math way to calculate the intersects between
    a line and the ellipse, then use the intersects to find the related pupil position.
    This part calculation is in the Eyes class. There is a lookAt2() method that
    is calculate in the circle way, please ignore that.


Deploy note:
    Please remove package while deploying.


One little bug:
    In order to close the eyes which will block the pupil, the way I came up is put 2
    rectangles at the top and bottom of eyes, when eyes blink, the 2 rectangles will
    enlarge or shrink corresponding to the eye blink. The bug is when you make pupils
    look at the corner of the eyes, while blink, it will see the pupils are not closed
    perfectly.