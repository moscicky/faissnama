# Faissnama

This project shows how new `java.foreign` API (project Panama) can be used to 
call C/C++ library - Faiss - from Java.

## Step 1 - build & install faiss

1. Download faiss

    MacOS and Ubuntu:
    ```shell
    git clone https://github.com/facebookresearch/faiss.git
    cd faiss
    ```

2. Install dependencies

    MacOS:
    ```shell
    brew install libomp
    ```
   
    Ubuntu:
    ```shell
    sudo apt-get install libomp-dev libblas-dev liblapack-dev
    sudo snap install cmake --classic # the version in apt is too old
    ```

3. Create build configuration

    MacOS:
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

    Ubuntu:
    ```shell
    cmake . -B build \
    -DFAISS_ENABLE_GPU=OFF \
    -DFAISS_ENABLE_PYTHON=OFF \
    -DFAISS_OPT_LEVEL=avx2 \
    -DFAISS_ENABLE_C_API=ON \
    -DBUILD_SHARED_LIBS=ON    
    ```
   
4. Build faiss

    MacOS and Ubuntu:
    ```shell
    cmake --build build --config Release -j
    ```

5. build & install faiss C API

    MacOS:
    ```shell
    cd build/c_api && make && sudo make install
    sudo cp libfaiss_c.dylib /usr/local/lib/  
    ```
   
    Ubuntu:
    ```shell
    cd build/c_api && make && sudo make install
    sudo cp libfaiss_c.so /usr/local/lib/  
    ```

## Step 2 - generate C bindings

1. Download & extract jextract

    MacOS:
    ```shell
    wget https://download.java.net/java/early_access/jextract/1/openjdk-20-jextract+1-2_macos-x64_bin.tar.gz
    && tar -xzf openjdk-20-jextract+1-2_macos-x64_bin.tar.gz
    ```
   
    Ubuntu:
    ```shell
    wget https://download.java.net/java/early_access/jextract/1/openjdk-20-jextract+1-2_linux-x64_bin.tar.gz
    && tar -xzf openjdk-20-jextract+1-2_linux-x64_bin.tar.gz
    ```

2. Generate faiss C bindings (they will be created in `faiss` directory)

    MacOS:
    ```shell
    cd scripts && ./create_bindings.sh
    ```

    Ubuntu:
    ```shell
    cd scripts && ./create_bindings_ubuntu.sh
    ```

### Reference

- https://github.com/carldea/panama4newbies
