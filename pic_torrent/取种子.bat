xcopy /s .\pic\*.* .\processed\pic\
java P2t p2t
xcopy /s .\processed\torrent\*.* .\torrent\
del /s /f /a /q .\processed\torrent\*
del /s /f /a /q .\processed\pic\*
echo ³É¹¦£¡
PAUSE