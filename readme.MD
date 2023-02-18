# Faissnama

This project shows how new `java.foreign` API (project Panama) can be used to 
call C/C++ library - Faiss - from Java.

## MaOS

### Step 1 - build & install faiss

1. Download faiss

    ```shell
    git clone https://github.com/facebookresearch/faiss.git
    cd faiss
    ```

2. Install OpenMP

    ```shell
    brew install libomp
    ```

3. Create build directory

    ```shell
    cmake -B build \
    -DFAISS_ENABLE_GPU=OFF \
    -DFAISS_ENABLE_PYTHON=OFF \
    -DBUILD_TESTING=OFF \
    -DBUILD_SHARED_LIBS=ON \
    -DCMAKE_BUILD_TYPE=Release \
    -DFAISS_OPT_LEVEL=avx2 \
    -DFAISS_ENABLE_C_API=ON \
    -DOpenMP_C_FLAGS=-fopenmp=lomp \
    -DOpenMP_CXX_FLAGS=-fopenmp=lomp \
    -DOpenMP_C_LIB_NAMES="libomp" -DOpenMP_CXX_LIB_NAMES="libomp" \
    -DOpenMP_libomp_LIBRARY="/opt/homebrew/opt/libomp/lib/libomp.dylib" \
    -DOpenMP_CXX_FLAGS="-Xpreprocessor -fopenmp /opt/homebrew/opt/libomp/lib/libomp.dylib \
    -I/opt/homebrew/opt/libomp/include" -DOpenMP_CXX_LIB_NAMES="libomp" \
    -DOpenMP_C_FLAGS="-Xpreprocessor -fopenmp  --I/opt/homebrew/opt/libomp/include"
    .
    ```

4. Build faiss

    ```shell
   make -k -C build -j faiss
   ```

5. build & install faiss C API
    ```shell
    cd build/c_api && make && sudo make install
    sudo cp libfaiss_c.dylib /usr/local/lib/  
    ```

## Step 2 - generate C bindings

1. Download & extract jextract

   ```shell
   wget https://download.java.net/java/early_access/jextract/2/openjdk-19-jextract+2-3_macos-x64_bin.tar.gz
   && tar -xzf openjdk-19-jextract+2-3_macos-x64_bin.tar.gz
   ```

2. Generate faiss C bindings (they will be created in `faiss` directory)

   ```shell
   cd scripts && ./create_bindings.sh
   ```

## Linux
todo
   
### Reference

- https://github.com/carldea/panama4newbies

