# http://stackoverflow.com/questions/442188/readint-readbyte-readstring-etc-in-python/4338551#4338551

# Note: Java DataInputStream/DataOutputStream is big-endian

from struct import *

class BinaryStream:
    def __init__(self, base_stream):
        self.base_stream = base_stream

    def readByte(self):
        return self.base_stream.read(1)

    def readBytes(self, length):
        return self.base_stream.read(length)

    def readChar(self):
        return self.unpack('>b')

    def readUChar(self):
        return self.unpack('>B')

    def readBool(self):
        return self.unpack('>?')

    def readInt16(self):
        return self.unpack('>h', 2)

    def readUInt16(self):
        return self.unpack('>H', 2)

    def readInt32(self):
        return self.unpack('>i', 4)

    def readUInt32(self):
        return self.unpack('>I', 4)

    def readInt64(self):
        return self.unpack('>q', 8)

    def readUInt64(self):
        return self.unpack('>Q', 8)

    def readFloat(self):
        return self.unpack('>f', 4)

    def readDouble(self):
        return self.unpack('>d', 8)

    def readString(self):
        length = self.readUInt16()
        return self.unpack(str(length) + 's', length)

    def writeBytes(self, value):
        self.base_stream.write(value)

    def writeChar(self, value):
        self.pack('>b', value)

    def writeUChar(self, value):
        self.pack('>B', value)

    def writeBool(self, value):
        self.pack('>?', value)

    def writeInt16(self, value):
        self.pack('>h', value)

    def writeUInt16(self, value):
        self.pack('>H', value)

    def writeInt32(self, value):
        self.pack('>i', value)

    def writeUInt32(self, value):
        self.pack('>I', value)

    def writeInt64(self, value):
        self.pack('>q', value)

    def writeUInt64(self, value):
        self.pack('>Q', value)

    def writeFloat(self, value):
        self.pack('>f', value)

    def writeDouble(self, value):
        self.pack('>d', value)

    def writeString(self, value):
        length = len(value)
        self.writeUInt16(length)
        self.pack(str(length) + 's', value)

    def pack(self, fmt, data):
        return self.writeBytes(pack(fmt, data))

    def unpack(self, fmt, length = 1):
        return unpack(fmt, self.readBytes(length))[0]