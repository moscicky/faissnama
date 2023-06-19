sh $JEXTRACT_PATH \
--output ../faiss \
-t fb.faiss \
-I /usr/include \
-I /usr/local/include/faiss/c_api \
-l faiss_c \
--header-class-name faiss \
../headers.h
