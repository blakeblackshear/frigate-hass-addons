
FROM debian:11 as base
ENV DEBIAN_FRONTEND=noninteractive
# tflite dependencies, see https://github.com/tensorflow/tensorflow/tree/master/tensorflow/lite/tools/pip_package
# libedgetpu dependencies, see https://github.com/google-coral/libedgetpu/blob/master/docker/Dockerfile
RUN apt-get -qq update \
    && apt-get -qq install -y \
    python3 \
    python3-dev \
    python3-numpy \
    python3-pip \
    python3-pybind11 \
    build-essential cmake git pkg-config \
    curl unzip \
    swig libjpeg-dev zlib1g-dev \
    libusb-1.0-0-dev xxd

FROM base as build_libedgetpu
RUN git clone https://github.com/google-coral/libedgetpu.git
RUN cd libedgetpu && git checkout release-grouper
RUN echo "deb [arch=amd64] https://storage.googleapis.com/bazel-apt stable jdk1.8" > /etc/apt/sources.list.d/bazel.list \
    && curl -s https://storage.googleapis.com/bazel-apt/doc/apt-key.pub.gpg | apt-key add - \
    && apt-get -qq update \
    && apt-get -qq install bazel=3.2.0
RUN cd /libedgetpu && bazel fetch --experimental_repo_remote_exec @coral_crosstool//...
RUN sed -i 's/"-msse4.2"//g' $HOME/.cache/bazel/*/*/external/coral_crosstool/cc_toolchain_config.bzl.tpl
RUN printf "build --copt=-march=native\nbuild --host_copt=-march=native\n" > ~/.bazelrc
RUN cd /libedgetpu && make libedgetpu
RUN apt-get -qq install debhelper
RUN cd /libedgetpu && dpkg-buildpackage -rfakeroot -us -uc -tc -b

FROM base as build_tflite
RUN git clone https://github.com/tensorflow/tensorflow.git
RUN cd tensorflow && git checkout v2.5.1
RUN bash tensorflow/tensorflow/lite/tools/make/download_dependencies.sh
RUN env BUILD_NUM_JOBS=$(($(grep -c "^processor" /proc/cpuinfo) / 2)) bash tensorflow/tensorflow/lite/tools/pip_package/build_pip_package_with_cmake.sh

FROM ghcr.io/blakeblackshear/frigate:0.13.1
COPY --from=build_libedgetpu /libedgetpu1-max_16.0_amd64.deb /
RUN dpkg -i /libedgetpu1-max_16.0_amd64.deb
COPY --from=build_tflite /tensorflow/tensorflow/lite/tools/pip_package/gen/tflite_pip/python3/dist/tflite_runtime-2.5.1-cp39-cp39-linux_x86_64.whl /wheels/
RUN pip3 install --upgrade /wheels/tflite_runtime-2.5.1-cp39-cp39-linux_x86_64.whl
RUN pip3 install numpy==1.23.5
