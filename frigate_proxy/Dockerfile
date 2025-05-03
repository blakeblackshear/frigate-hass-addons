FROM ghcr.io/hassio-addons/debian-base:7.8.2

# Setup base
# hadolint ignore=DL3003
RUN apt-get -qq update \
    && apt-get install -qq --no-install-recommends nginx \
    && rm -rf /var/lib/apt/lists/*

# Copy root filesystem
COPY rootfs /
