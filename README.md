# DesktopBGChanger

This is a tool for exchanging the photos that windows uses for the desktop background.

Windows supports only one folder to read the pictures from but I want to use a set of
folders from my photo collection. I google'd but there was nothing appropriate for me
(using windows 10). So i wrote this tool. First I thought of a backgroud service that
exchanges photos but unfortunately windows detects changes only if they are done using
the windows explorer. I searched for registry entries but I didn't find anything that
worked. So I took another approach: Since I detected that windows scans the desktop
background folder on log in, I changed the way to put my computer in hibernate mode.
This program calls the windows command 'shutdown /h' and before it does, it exchanges
the photos in the dektop background folder. And next time I turn the computer on I have
new photos on my desktop background.

In order to make this work useful for others too, I decided to separate the program in
two parts. The first one called DesktopBGChangerGui is a small Gui where the configuration
is done. The configuration is stored in an xml file and it is read from the second part 
(called DesktopBGChanger) that copies pictures from the configured folders to the desktop 
backgound folder. Once the configuration is done you only need to use the DesktopBGChanger
as a button to shutdown your computer. The way the computer shuts down may be configured.
You can choose between hibernate, power off, sign off or simply do nothing.

Have fun
Ralf
