xcopy  /s .\torrent\*.* .\raw\torrent\
java P2t t2p
xcopy /s .\processed\pic\*.* .\pic
del /a /f /s /q .\processed\pic\*
del /a /f /s /q .\raw\torrent\*
echo ³É¹¦£¡
PAUSE