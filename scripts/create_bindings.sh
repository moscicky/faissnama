# bin/sh

source .env

sh $JEXTRACT_PATH \
--output ../faiss \
-t fb.faiss \
-I /Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/usr/include \
-I /usr/local/include/faiss/c_api \
-l faiss_c \
--header-class-name faiss \
../headers.h
