# Faissnama

```shell
make build
```

```shell
make run
```

```shell
cd jextract/bin/
./jextract --output src -t com.faiss /usr/local/include/faiss/c_api/index_io_c.h 
```

```shell
 ./jextract --output src -t test.faiss -I /usr/include -I /usr/local/include/faiss/c_api /usr/local/include/faiss/c_api/index_io_c.h 
```

```shell
./jextract --output src -t test.faiss -I /usr/include -I /usr/lib/gcc/x86_64-linux-gnu/11/include -I /usr/local/include/faiss/c_api /usr/local/include/faiss/c_api/index_factory_c.h 
```