ARG BUILD_ARCH=amd64

FROM ubuntu:20.04 as base
ENV DEBIAN_FRONTEND=noninteractive
# tflite dependencies, see https://github.com/tensorflow/tensorflow/tree/master/tensorflow/lite/tools/pip_package
# libedgetpu dependencies, see https://github.com/google-coral/libedgetpu/blob/master/docker/Dockerfile
RUN apt-get -qq update \
    && apt-get -qq install -y \
    python3 \
    python3-dev \
    python3-pip \
    build-essential cmake git pkg-config \
    curl unzip \
    swig libjpeg-dev zlib1g-dev \
    libusb-1.0-0-dev xxd

FROM base as build_libedgetpu
RUN git clone https://github.com/google-coral/libedgetpu.git
RUN cd libedgetpu && git checkout 14eee1a076aa1af7ec1ae3c752be79ae2604a708
RUN echo "deb [arch=amd64] https://storage.googleapis.com/bazel-apt stable jdk1.8" > /etc/apt/sources.list.d/bazel.list \
    && curl -s https://storage.googleapis.com/bazel-apt/doc/apt-key.pub.gpg | apt-key add - \
    && apt-get -qq update \
    && apt-get -qq install bazel=3.2.0
RUN cd /libedgetpu && bazel fetch --experimental_repo_remote_exec @coral_crosstool//...
RUN sed -i 's/"-msse4.2",//g' $HOME/.cache/bazel/*/*/external/coral_crosstool/cc_toolchain_config.bzl.tpl
RUN printf "build --copt=-march=native\nbuild --host_copt=-march=native\n" > ~/.bazelrc
RUN cd /libedgetpu && make libedgetpu
RUN apt-get -qq install debhelper
RUN cd /libedgetpu && make deb

FROM base as build_tflite
RUN git clone https://github.com/tensorflow/tensorflow.git
RUN cd tensorflow && git checkout 8ae2eecf4f70b5efb55a108f2b0000ea6cad3e05
RUN pip3 install -U \
    # pin to the same version that frigate pins, see https://github.com/blakeblackshear/frigate/blob/v0.8.1/docker/Dockerfile.wheels#L27
    numpy==1.19.4 \
    pybind11
RUN bash tensorflow/tensorflow/lite/tools/make/download_dependencies.sh
RUN bash tensorflow/tensorflow/lite/tools/pip_package/build_pip_package.sh

FROM blakeblackshear/frigate:0.8.4-${BUILD_ARCH}
COPY --from=build_libedgetpu /libedgetpu1-max_14.0_amd64.deb /
RUN dpkg -i /libedgetpu1-max_14.0_amd64.deb
COPY --from=build_tflite /tensorflow/tensorflow/lite/tools/pip_package/gen/tflite_pip/python3/dist/tflite_runtime-2.5.0-cp38-cp38-linux_x86_64.whl /wheels/
RUN pip3 install --upgrade /wheels/tflite_runtime-2.5.0-cp38-cp38-linux_x86_64.whl
