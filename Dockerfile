FROM ubuntu:22.10

RUN apt-get update \
    && apt-get install -y \
    software-properties-common \
    build-essential \
    wget \
    git \
    libopenblas-dev

# install cmake
RUN wget https://github.com/Kitware/CMake/releases/download/v3.24.1/cmake-3.24.1-Linux-x86_64.sh \
          -q -O /tmp/cmake-install.sh \
          && chmod u+x /tmp/cmake-install.sh \
          && mkdir /opt/cmake-3.24.1 \
          && /tmp/cmake-install.sh --skip-license --prefix=/opt/cmake-3.24.1 \
          && rm /tmp/cmake-install.sh \
          && ln -s /opt/cmake-3.24.1/bin/* /usr/local/bin

# install gcc 11
RUN add-apt-repository -y ppa:ubuntu-toolchain-r/test \
    && apt-get update \
    && apt-get install -y gcc-11 g++-11 \
    && update-alternatives --install /usr/bin/g++ g++ /usr/bin/g++-11 100 \

# download jextract
RUN wget https://download.java.net/java/early_access/jextract/2/openjdk-19-jextract+2-3_linux-x64_bin.tar.gz \
&& tar -xzvf openjdk-19-jextract+2-3_linux-x64_bin.tar.gz

# install java 19 temurin
RUN mkdir -p /etc/apt/keyrings \
    && wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | tee /etc/apt/keyrings/adoptium.asc \
    && echo "deb [signed-by=/etc/apt/keyrings/adoptium.asc] https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list \
    && apt update \
    && apt install -y temurin-19-jdk

# download and install faiss c_api
RUN git clone https://github.com/facebookresearch/faiss.git

WORKDIR faiss

RUN cmake -B build \
    -DFAISS_ENABLE_GPU=OFF \
    -DFAISS_ENABLE_PYTHON=OFF \
    -DBUILD_TESTING=OFF \
    -DBUILD_SHARED_LIBS=ON \
    -DCMAKE_BUILD_TYPE=Release \
    -DFAISS_OPT_LEVEL=avx2 \
    -DFAISS_ENABLE_C_API=ON \
    .

RUN make -C build -j4 faiss

RUN cd build/c_api  \
    && make \
    && make install

ENTRYPOINT ["echo", "hello"]