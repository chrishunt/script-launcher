require 'socket'

hostname = 'localhost'
port = '3010'

begin
  socket = TCPSocket.open(hostname, port)
  socket.puts "hellolocalhost"
  socket.puts "/Users/huntca/Dropbox/src/progeny/win-script-launcher/test.sh"
  puts socket.gets
rescue
  puts "Error connecting to server."
end
