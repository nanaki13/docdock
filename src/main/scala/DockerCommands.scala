package bon.jo

import bon.jo.model._

object DockerCommands {

  object `d_builder` extends Command("builder", "Manage builds") {

    override val options = Some(List())
    override val usage = Some("""docker builder COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_config` extends Command("config", "Manage Docker configs") {

    override val options = Some(List())
    override val usage = Some("""docker config COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_container` extends Command("container", "Manage containers") {

    override val options = Some(List())
    override val usage = Some("""docker container COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_context` extends Command("context", "Manage contexts") {

    override val options = Some(List())
    override val usage = Some("""docker context COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_image` extends Command("image", "Manage images") {

    override val options = Some(List())
    override val usage = Some("""docker image COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_network` extends Command("network", "Manage networks") {

    override val options = Some(List())
    override val usage = Some("""docker network COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_node` extends Command("node", "Manage Swarm nodes") {

    override val options = Some(List())
    override val usage = Some("""docker node COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_plugin` extends Command("plugin", "Manage plugins") {

    override val options = Some(List())
    override val usage = Some("""docker plugin COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_secret` extends Command("secret", "Manage Docker secrets") {

    override val options = Some(List())
    override val usage = Some("""docker secret COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_service` extends Command("service", "Manage services") {

    override val options = Some(List())
    override val usage = Some("""docker service COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_stack` extends Command("stack", "Manage Docker stacks") {

    object `orchestrator` extends CommandOption("--orchestrator string","""Orchestrator to use (swarm|kubernetes|all)  Commands: deployDeploy a new stack or update an existing stack ls List stacks ps List the tasks in the stack rm Remove one or more stacks services List the services in the stack  Run 'docker stack COMMAND --help' for more information on a command.""")

    override val options = Some(List(`orchestrator`))
    override val usage = Some("""docker stack [OPTIONS] COMMAND""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`[OPTIONS]`, `COMMAND`))
  }

  object `d_swarm` extends Command("swarm", "Manage Swarm") {

    override val options = Some(List())
    override val usage = Some("""docker swarm COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_system` extends Command("system", "Manage Docker") {

    override val options = Some(List())
    override val usage = Some("""docker system COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_trust` extends Command("trust", "Manage trust on Docker images") {

    override val options = Some(List())
    override val usage = Some("""docker trust COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_volume` extends Command("volume", "Manage volumes") {

    override val options = Some(List())
    override val usage = Some("""docker volume COMMAND""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    override val usageDesc = Some(List(`COMMAND`))
  }

  object `d_attach` extends Command("attach", "Attach local standard input, output, and error streams to a running container") {

    object `detach-keys` extends CommandOption("--detach-keys string","""Override the key sequence for detaching a container""")

    object `no-stdin` extends CommandOption("--no-stdin","""Do not attach STDIN""")

    object `sig-proxy` extends CommandOption("--sig-proxy","""Proxy all received signals to the process (default true)""")

    override val options = Some(List(`detach-keys`, `no-stdin`, `sig-proxy`))
    override val usage = Some("""docker attach [OPTIONS] CONTAINER""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`))
  }

  object `d_build` extends Command("build", "Build an image from a Dockerfile") {

    object `add-host` extends CommandOption("--add-host list","""Add a custom host-to-IP mapping (host:ip)""")

    object `build-arg` extends CommandOption("--build-arg list","""Set build-time variables""")

    object `cache-from` extends CommandOption("--cache-from strings","""Images to consider as cache sources""")

    object `cgroup-parent` extends CommandOption("--cgroup-parent string","""Optional parent cgroup for the container""")

    object `compress` extends CommandOption("--compress","""Compress the build context using gzip""")

    object `cpu-period` extends CommandOption("--cpu-period int","""Limit the CPU CFS (Completely Fair Scheduler) period""")

    object `cpu-quota` extends CommandOption("--cpu-quota int","""Limit the CPU CFS (Completely Fair Scheduler) quota""")

    object `cpu-shares` extends CommandOption("-c, --cpu-shares int","""CPU shares (relative weight)""")

    object `cpuset-cpus` extends CommandOption("--cpuset-cpus string","""CPUs in which to allow execution (0-3, 0,1)""")

    object `cpuset-mems` extends CommandOption("--cpuset-mems string","""MEMs in which to allow execution (0-3, 0,1)""")

    object `disable-content-trust` extends CommandOption("--disable-content-trust","""Skip image verification (default true)""")

    object `file` extends CommandOption("-f, --file string","""Name of the Dockerfile (Default is 'PATH/Dockerfile')""")

    object `force-rm` extends CommandOption("--force-rm","""Always remove intermediate containers""")

    object `iidfile` extends CommandOption("--iidfile string","""Write the image ID to the file""")

    object `isolation` extends CommandOption("--isolation string","""Container isolation technology""")

    object `label` extends CommandOption("--label list","""Set metadata for an image""")

    object `memory` extends CommandOption("-m, --memory bytes","""Memory limit""")

    object `memory-swap` extends CommandOption("--memory-swap bytes","""Swap limit equal to memory plus swap: '-1' to enable unlimited swap""")

    object `network` extends CommandOption("--network string","""Set the networking mode for the RUN instructions during build (default "default")""")

    object `no-cache` extends CommandOption("--no-cache","""Do not use cache when building the image""")

    object `pull` extends CommandOption("--pull","""Always attempt to pull a newer version of the image""")

    object `quiet` extends CommandOption("-q, --quiet","""Suppress the build output and print image ID on success""")

    object `rm` extends CommandOption("--rm","""Remove intermediate containers after a successful build (default true)""")

    object `security-opt` extends CommandOption("--security-opt strings","""Security options""")

    object `shm-size` extends CommandOption("--shm-size bytes","""Size of /dev/shm""")

    object `tag` extends CommandOption("-t, --tag list","""Name and optionally a tag in the 'name:tag' format""")

    object `target` extends CommandOption("--target string","""Set the target build stage to build.""")

    object `ulimit` extends CommandOption("--ulimit ulimit","""Ulimit options (default [])""")

    override val options = Some(List(`add-host`, `build-arg`, `cache-from`, `cgroup-parent`, `compress`, `cpu-period`, `cpu-quota`, `cpu-shares`, `cpuset-cpus`, `cpuset-mems`, `disable-content-trust`, `file`, `force-rm`, `iidfile`, `isolation`, `label`, `memory`, `memory-swap`, `network`, `no-cache`, `pull`, `quiet`, `rm`, `security-opt`, `shm-size`, `tag`, `target`, `ulimit`))
    override val usage = Some("""docker build [OPTIONS] PATH | URL | -""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `UsageDesc(UsageDesc(PATH)|URL)|-` extends UsageDesc("""UsageDesc(UsageDesc(PATH)|URL)|-""")

    override val usageDesc = Some(List(`[OPTIONS]`, `UsageDesc(UsageDesc(PATH)|URL)|-`))
  }

  object `d_commit` extends Command("commit", "Create a new image from a container's changes") {

    object `author` extends CommandOption("-a, --author string","""Author (e.g., "John Hannibal Smith ")""")

    object `change` extends CommandOption("-c, --change list","""Apply Dockerfile instruction to the created image""")

    object `message` extends CommandOption("-m, --message string","""Commit message""")

    object `pause` extends CommandOption("-p, --pause","""Pause container during commit (default true)""")

    override val options = Some(List(`author`, `change`, `message`, `pause`))
    override val usage = Some("""docker commit [OPTIONS] CONTAINER [REPOSITORY[:TAG]]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[REPOSITORY[:TAG]]` extends UsageDesc("""[REPOSITORY[:TAG]]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`, `[REPOSITORY[:TAG]]`))
  }

  object `d_cp` extends Command("cp", "Copy files/folders between a container and the local filesystem") {

    object `archive` extends CommandOption("-a, --archive","""Archive mode (copy all uid/gid information)""")

    object `follow-link` extends CommandOption("-L, --follow-link","""Always follow symbol link in SRC_PATH""")

    override val options = Some(List(`archive`, `follow-link`))
    override val usage = Some("""docker cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH|-""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER:SRC_PATH` extends UsageDesc("""CONTAINER:SRC_PATH""")

    object `DEST_PATH|-` extends UsageDesc("""DEST_PATH|-""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER:SRC_PATH`, `DEST_PATH|-`))
  }

  object `d_create` extends Command("create", "Create a new container") {

    object `add-host` extends CommandOption("--add-host list","""Add a custom host-to-IP mapping (host:ip)""")

    object `attach` extends CommandOption("-a, --attach list","""Attach to STDIN, STDOUT or STDERR""")

    object `blkio-weight` extends CommandOption("--blkio-weight uint16","""Block IO (relative weight), between 10 and 1000, or 0 to disable (default 0)""")

    object `blkio-weight-device` extends CommandOption("--blkio-weight-device list","""Block IO weight (relative device weight) (default [])""")

    object `cap-add` extends CommandOption("--cap-add list","""Add Linux capabilities""")

    object `cap-drop` extends CommandOption("--cap-drop list","""Drop Linux capabilities""")

    object `cgroup-parent` extends CommandOption("--cgroup-parent string","""Optional parent cgroup for the container""")

    object `cidfile` extends CommandOption("--cidfile string","""Write the container ID to the file""")

    object `cpu-period` extends CommandOption("--cpu-period int","""Limit CPU CFS (Completely Fair Scheduler) period""")

    object `cpu-quota` extends CommandOption("--cpu-quota int","""Limit CPU CFS (Completely Fair Scheduler) quota""")

    object `cpu-rt-period` extends CommandOption("--cpu-rt-period int","""Limit CPU real-time period in microseconds""")

    object `cpu-rt-runtime` extends CommandOption("--cpu-rt-runtime int","""Limit CPU real-time runtime in microseconds""")

    object `cpu-shares` extends CommandOption("-c, --cpu-shares int","""CPU shares (relative weight)""")

    object `cpus` extends CommandOption("--cpus decimal","""Number of CPUs""")

    object `cpuset-cpus` extends CommandOption("--cpuset-cpus string","""CPUs in which to allow execution (0-3, 0,1)""")

    object `cpuset-mems` extends CommandOption("--cpuset-mems string","""MEMs in which to allow execution (0-3, 0,1)""")

    object `device` extends CommandOption("--device list","""Add a host device to the container""")

    object `device-cgroup-rule` extends CommandOption("--device-cgroup-rule list","""Add a rule to the cgroup allowed devices list""")

    object `device-read-bps` extends CommandOption("--device-read-bps list","""Limit read rate (bytes per second) from a device (default [])""")

    object `device-read-iops` extends CommandOption("--device-read-iops list","""Limit read rate (IO per second) from a device (default [])""")

    object `device-write-bps` extends CommandOption("--device-write-bps list","""Limit write rate (bytes per second) to a device (default [])""")

    object `device-write-iops` extends CommandOption("--device-write-iops list","""Limit write rate (IO per second) to a device (default [])""")

    object `disable-content-trust` extends CommandOption("--disable-content-trust","""Skip image verification (default true)""")

    object `dns` extends CommandOption("--dns list","""Set custom DNS servers""")

    object `dns-option` extends CommandOption("--dns-option list","""Set DNS options""")

    object `dns-search` extends CommandOption("--dns-search list","""Set custom DNS search domains""")

    object `domainname` extends CommandOption("--domainname string","""Container NIS domain name""")

    object `entrypoint` extends CommandOption("--entrypoint string","""Overwrite the default ENTRYPOINT of the image""")

    object `env` extends CommandOption("-e, --env list","""Set environment variables""")

    object `env-file` extends CommandOption("--env-file list","""Read in a file of environment variables""")

    object `expose` extends CommandOption("--expose list","""Expose a port or a range of ports""")

    object `gpus` extends CommandOption("--gpus gpu-request","""GPU devices to add to the container ('all' to pass all GPUs)""")

    object `group-add` extends CommandOption("--group-add list","""Add additional groups to join""")

    object `health-cmd` extends CommandOption("--health-cmd string","""Command to run to check health""")

    object `health-interval` extends CommandOption("--health-interval duration","""Time between running the check (ms|s|m|h) (default 0s)""")

    object `health-retries` extends CommandOption("--health-retries int","""Consecutive failures needed to report unhealthy""")

    object `health-start-period` extends CommandOption("--health-start-period duration","""Start period for the container to initialize before starting health-retries countdown (ms|s|m|h) (default 0s)""")

    object `health-timeout` extends CommandOption("--health-timeout duration","""Maximum time to allow one check to run (ms|s|m|h) (default 0s)""")

    object `help` extends CommandOption("--help","""Print usage""")

    object `hostname` extends CommandOption("-h, --hostname string","""Container host name""")

    object `init` extends CommandOption("--init","""Run an init inside the container that forwards signals and reaps processes""")

    object `interactive` extends CommandOption("-i, --interactive","""Keep STDIN open even if not attached""")

    object `ip` extends CommandOption("--ip string","""IPv4 address (e.g., 172.30.100.104)""")

    object `ip6` extends CommandOption("--ip6 string","""IPv6 address (e.g., 2001:db8::33)""")

    object `ipc` extends CommandOption("--ipc string","""IPC mode to use""")

    object `isolation` extends CommandOption("--isolation string","""Container isolation technology""")

    object `kernel-memory` extends CommandOption("--kernel-memory bytes","""Kernel memory limit""")

    object `label` extends CommandOption("-l, --label list","""Set meta data on a container""")

    object `label-file` extends CommandOption("--label-file list","""Read in a line delimited file of labels""")

    object `link` extends CommandOption("--link list","""Add link to another container""")

    object `link-local-ip` extends CommandOption("--link-local-ip list","""Container IPv4/IPv6 link-local addresses""")

    object `log-driver` extends CommandOption("--log-driver string","""Logging driver for the container""")

    object `log-opt` extends CommandOption("--log-opt list","""Log driver options""")

    object `mac-address` extends CommandOption("--mac-address string","""Container MAC address (e.g., 92:d0:c6:0a:29:33)""")

    object `memory` extends CommandOption("-m, --memory bytes","""Memory limit""")

    object `memory-reservation` extends CommandOption("--memory-reservation bytes","""Memory soft limit""")

    object `memory-swap` extends CommandOption("--memory-swap bytes","""Swap limit equal to memory plus swap: '-1' to enable unlimited swap""")

    object `memory-swappiness` extends CommandOption("--memory-swappiness int","""Tune container memory swappiness (0 to 100) (default -1)""")

    object `mount` extends CommandOption("--mount mount","""Attach a filesystem mount to the container""")

    object `name` extends CommandOption("--name string","""Assign a name to the container""")

    object `network` extends CommandOption("--network network","""Connect a container to a network""")

    object `network-alias` extends CommandOption("--network-alias list","""Add network-scoped alias for the container""")

    object `no-healthcheck` extends CommandOption("--no-healthcheck","""Disable any container-specified HEALTHCHECK""")

    object `oom-kill-disable` extends CommandOption("--oom-kill-disable","""Disable OOM Killer""")

    object `oom-score-adj` extends CommandOption("--oom-score-adj int","""Tune host's OOM preferences (-1000 to 1000)""")

    object `pid` extends CommandOption("--pid string","""PID namespace to use""")

    object `pids-limit` extends CommandOption("--pids-limit int","""Tune container pids limit (set -1 for unlimited)""")

    object `privileged` extends CommandOption("--privileged","""Give extended privileges to this container""")

    object `publish` extends CommandOption("-p, --publish list","""Publish a container's port(s) to the host""")

    object `publish-all` extends CommandOption("-P, --publish-all","""Publish all exposed ports to random ports""")

    object `read-only` extends CommandOption("--read-only","""Mount the container's root filesystem as read only""")

    object `restart` extends CommandOption("--restart string","""Restart policy to apply when a container exits (default "no")""")

    object `rm` extends CommandOption("--rm","""Automatically remove the container when it exits""")

    object `runtime` extends CommandOption("--runtime string","""Runtime to use for this container""")

    object `security-opt` extends CommandOption("--security-opt list","""Security Options""")

    object `shm-size` extends CommandOption("--shm-size bytes","""Size of /dev/shm""")

    object `stop-signal` extends CommandOption("--stop-signal string","""Signal to stop a container (default "15")""")

    object `stop-timeout` extends CommandOption("--stop-timeout int","""Timeout (in seconds) to stop a container""")

    object `storage-opt` extends CommandOption("--storage-opt list","""Storage driver options for the container""")

    object `sysctl` extends CommandOption("--sysctl map","""Sysctl options (default map[])""")

    object `tmpfs` extends CommandOption("--tmpfs list","""Mount a tmpfs directory""")

    object `tty` extends CommandOption("-t, --tty","""Allocate a pseudo-TTY""")

    object `ulimit` extends CommandOption("--ulimit ulimit","""Ulimit options (default [])""")

    object `user` extends CommandOption("-u, --user string","""Username or UID (format: [:])""")

    object `userns` extends CommandOption("--userns string","""User namespace to use""")

    object `uts` extends CommandOption("--uts string","""UTS namespace to use""")

    object `volume` extends CommandOption("-v, --volume list","""Bind mount a volume""")

    object `volume-driver` extends CommandOption("--volume-driver string","""Optional volume driver for the container""")

    object `volumes-from` extends CommandOption("--volumes-from list","""Mount volumes from the specified container(s)""")

    object `workdir` extends CommandOption("-w, --workdir string","""Working directory inside the container""")

    override val options = Some(List(`add-host`, `attach`, `blkio-weight`, `blkio-weight-device`, `cap-add`, `cap-drop`, `cgroup-parent`, `cidfile`, `cpu-period`, `cpu-quota`, `cpu-rt-period`, `cpu-rt-runtime`, `cpu-shares`, `cpus`, `cpuset-cpus`, `cpuset-mems`, `device`, `device-cgroup-rule`, `device-read-bps`, `device-read-iops`, `device-write-bps`, `device-write-iops`, `disable-content-trust`, `dns`, `dns-option`, `dns-search`, `domainname`, `entrypoint`, `env`, `env-file`, `expose`, `gpus`, `group-add`, `health-cmd`, `health-interval`, `health-retries`, `health-start-period`, `health-timeout`, `help`, `hostname`, `init`, `interactive`, `ip`, `ip6`, `ipc`, `isolation`, `kernel-memory`, `label`, `label-file`, `link`, `link-local-ip`, `log-driver`, `log-opt`, `mac-address`, `memory`, `memory-reservation`, `memory-swap`, `memory-swappiness`, `mount`, `name`, `network`, `network-alias`, `no-healthcheck`, `oom-kill-disable`, `oom-score-adj`, `pid`, `pids-limit`, `privileged`, `publish`, `publish-all`, `read-only`, `restart`, `rm`, `runtime`, `security-opt`, `shm-size`, `stop-signal`, `stop-timeout`, `storage-opt`, `sysctl`, `tmpfs`, `tty`, `ulimit`, `user`, `userns`, `uts`, `volume`, `volume-driver`, `volumes-from`, `workdir`))
    override val usage = Some("""docker create [OPTIONS] IMAGE [COMMAND] [ARG...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `IMAGE` extends UsageDesc("""IMAGE""")

    object `[COMMAND]` extends UsageDesc("""[COMMAND]""")

    object `[ARG...]` extends UsageDesc("""[ARG...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `IMAGE`, `[COMMAND]`, `[ARG...]`))
  }

  object `d_diff` extends Command("diff", "Inspect changes to files or directories on a container's filesystem") {

    override val options = Some(List())
    override val usage = Some("""docker diff CONTAINER""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    override val usageDesc = Some(List(`CONTAINER`))
  }

  object `d_events` extends Command("events", "Get real time events from the server") {

    object `filter` extends CommandOption("-f, --filter filter","""Filter output based on conditions provided""")

    object `format` extends CommandOption("--format string","""Format the output using the given Go template""")

    object `since` extends CommandOption("--since string","""Show all events created since timestamp""")

    object `until` extends CommandOption("--until string","""Stream events until this timestamp""")

    override val options = Some(List(`filter`, `format`, `since`, `until`))
    override val usage = Some("""docker events [OPTIONS]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    override val usageDesc = Some(List(`[OPTIONS]`))
  }

  object `d_exec` extends Command("exec", "Run a command in a running container") {

    object `detach` extends CommandOption("-d, --detach","""Detached mode: run command in the background""")

    object `detach-keys` extends CommandOption("--detach-keys string","""Override the key sequence for detaching a container""")

    object `env` extends CommandOption("-e, --env list","""Set environment variables""")

    object `interactive` extends CommandOption("-i, --interactive","""Keep STDIN open even if not attached""")

    object `privileged` extends CommandOption("--privileged","""Give extended privileges to the command""")

    object `tty` extends CommandOption("-t, --tty","""Allocate a pseudo-TTY""")

    object `user` extends CommandOption("-u, --user string","""Username or UID (format: [:])""")

    object `workdir` extends CommandOption("-w, --workdir string","""Working directory inside the container""")

    override val options = Some(List(`detach`, `detach-keys`, `env`, `interactive`, `privileged`, `tty`, `user`, `workdir`))
    override val usage = Some("""docker exec [OPTIONS] CONTAINER COMMAND [ARG...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `COMMAND` extends UsageDesc("""COMMAND""")

    object `[ARG...]` extends UsageDesc("""[ARG...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`, `COMMAND`, `[ARG...]`))
  }

  object `d_export` extends Command("export", "Export a container's filesystem as a tar archive") {

    object `output` extends CommandOption("-o, --output string","""Write to a file, instead of STDOUT""")

    override val options = Some(List(`output`))
    override val usage = Some("""docker export [OPTIONS] CONTAINER""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`))
  }

  object `d_history` extends Command("history", "Show the history of an image") {

    object `format` extends CommandOption("--format string","""Pretty-print images using a Go template""")

    object `human` extends CommandOption("-H, --human","""Print sizes and dates in human readable format (default true)""")

    object `no-trunc` extends CommandOption("--no-trunc","""Don't truncate output""")

    object `quiet` extends CommandOption("-q, --quiet","""Only show numeric IDs""")

    override val options = Some(List(`format`, `human`, `no-trunc`, `quiet`))
    override val usage = Some("""docker history [OPTIONS] IMAGE""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `IMAGE` extends UsageDesc("""IMAGE""")

    override val usageDesc = Some(List(`[OPTIONS]`, `IMAGE`))
  }

  object `d_images` extends Command("images", "List images") {

    object `all` extends CommandOption("-a, --all","""Show all images (default hides intermediate images)""")

    object `digests` extends CommandOption("--digests","""Show digests""")

    object `filter` extends CommandOption("-f, --filter filter","""Filter output based on conditions provided""")

    object `format` extends CommandOption("--format string","""Pretty-print images using a Go template""")

    object `no-trunc` extends CommandOption("--no-trunc","""Don't truncate output""")

    object `quiet` extends CommandOption("-q, --quiet","""Only show numeric IDs""")

    override val options = Some(List(`all`, `digests`, `filter`, `format`, `no-trunc`, `quiet`))
    override val usage = Some("""docker images [OPTIONS] [REPOSITORY[:TAG]]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `[REPOSITORY[:TAG]]` extends UsageDesc("""[REPOSITORY[:TAG]]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `[REPOSITORY[:TAG]]`))
  }

  object `d_import` extends Command("import", "Import the contents from a tarball to create a filesystem image") {

    object `change` extends CommandOption("-c, --change list","""Apply Dockerfile instruction to the created image""")

    object `message` extends CommandOption("-m, --message string","""Set commit message for imported image""")

    override val options = Some(List(`change`, `message`))
    override val usage = Some("""docker import [OPTIONS] file|URL|- [REPOSITORY[:TAG]]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `file|URL|-` extends UsageDesc("""file|URL|-""")

    object `[REPOSITORY[:TAG]]` extends UsageDesc("""[REPOSITORY[:TAG]]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `file|URL|-`, `[REPOSITORY[:TAG]]`))
  }

  object `d_info` extends Command("info", "Display system-wide information") {

    object `format` extends CommandOption("-f, --format string","""Format the output using the given Go template""")

    override val options = Some(List(`format`))
    override val usage = Some("""docker info [OPTIONS]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    override val usageDesc = Some(List(`[OPTIONS]`))
  }

  object `d_inspect` extends Command("inspect", "Return low-level information on Docker objects") {

    object `format` extends CommandOption("-f, --format string","""Format the output using the given Go template""")

    object `size` extends CommandOption("-s, --size","""Display total file sizes if the type is container""")

    object `type` extends CommandOption("--type string","""Return JSON for specified type""")

    override val options = Some(List(`format`, `size`, `type`))
    override val usage = Some("""docker inspect [OPTIONS] NAME|ID [NAME|ID...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `NAME|ID` extends UsageDesc("""NAME|ID""")

    object `[NAME|ID...]` extends UsageDesc("""[NAME|ID...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `NAME|ID`, `[NAME|ID...]`))
  }

  object `d_kill` extends Command("kill", "Kill one or more running containers") {

    object `signal` extends CommandOption("-s, --signal string","""Signal to send to the container (default "KILL")""")

    override val options = Some(List(`signal`))
    override val usage = Some("""docker kill [OPTIONS] CONTAINER [CONTAINER...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`, `[CONTAINER...]`))
  }

  object `d_load` extends Command("load", "Load an image from a tar archive or STDIN") {

    object `input` extends CommandOption("-i, --input string","""Read from tar archive file, instead of STDIN""")

    object `quiet` extends CommandOption("-q, --quiet","""Suppress the load output""")

    override val options = Some(List(`input`, `quiet`))
    override val usage = Some("""docker load [OPTIONS]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    override val usageDesc = Some(List(`[OPTIONS]`))
  }

  object `d_login` extends Command("login", "Log in to a Docker registry") {

    object `password` extends CommandOption("-p, --password string","""Password""")

    object `password-stdin` extends CommandOption("--password-stdin","""Take the password from stdin""")

    object `username` extends CommandOption("-u, --username string","""Username""")

    override val options = Some(List(`password`, `password-stdin`, `username`))
    override val usage = Some("""docker login [OPTIONS] [SERVER]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `[SERVER]` extends UsageDesc("""[SERVER]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `[SERVER]`))
  }

  object `d_logout` extends Command("logout", "Log out from a Docker registry") {

    override val options = Some(List())
    override val usage = Some("""docker logout [SERVER]""")

    object `[SERVER]` extends UsageDesc("""[SERVER]""")

    override val usageDesc = Some(List(`[SERVER]`))
  }

  object `d_logs` extends Command("logs", "Fetch the logs of a container") {

    object `details` extends CommandOption("--details","""Show extra details provided to logs""")

    object `follow` extends CommandOption("-f, --follow","""Follow log output""")

    object `since` extends CommandOption("--since string","""Show logs since timestamp (e.g. 2013-01-02T13:23:37) or relative (e.g. 42m for 42 minutes)""")

    object `tail` extends CommandOption("--tail string","""Number of lines to show from the end of the logs (default "all")""")

    object `timestamps` extends CommandOption("-t, --timestamps","""Show timestamps""")

    object `until` extends CommandOption("--until string","""Show logs before a timestamp (e.g. 2013-01-02T13:23:37) or relative (e.g. 42m for 42 minutes)""")

    override val options = Some(List(`details`, `follow`, `since`, `tail`, `timestamps`, `until`))
    override val usage = Some("""docker logs [OPTIONS] CONTAINER""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`))
  }

  object `d_pause` extends Command("pause", "Pause all processes within one or more containers") {

    override val options = Some(List())
    override val usage = Some("""docker pause CONTAINER [CONTAINER...]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`CONTAINER`, `[CONTAINER...]`))
  }

  object `d_port` extends Command("port", "List port mappings or a specific mapping for the container") {

    override val options = Some(List())
    override val usage = Some("""docker port CONTAINER [PRIVATE_PORT[/PROTO]]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[PRIVATE_PORT[/PROTO]]` extends UsageDesc("""[PRIVATE_PORT[/PROTO]]""")

    override val usageDesc = Some(List(`CONTAINER`, `[PRIVATE_PORT[/PROTO]]`))
  }

  object `d_ps` extends Command("ps", "List containers") {

    object `all` extends CommandOption("-a, --all","""Show all containers (default shows just running)""")

    object `filter` extends CommandOption("-f, --filter filter","""Filter output based on conditions provided""")

    object `format` extends CommandOption("--format string","""Pretty-print containers using a Go template""")

    object `last` extends CommandOption("-n, --last int","""Show n last created containers (includes all states) (default -1)""")

    object `latest` extends CommandOption("-l, --latest","""Show the latest created container (includes all states)""")

    object `no-trunc` extends CommandOption("--no-trunc","""Don't truncate output""")

    object `quiet` extends CommandOption("-q, --quiet","""Only display numeric IDs""")

    object `size` extends CommandOption("-s, --size","""Display total file sizes""")

    override val options = Some(List(`all`, `filter`, `format`, `last`, `latest`, `no-trunc`, `quiet`, `size`))
    override val usage = Some("""docker ps [OPTIONS]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    override val usageDesc = Some(List(`[OPTIONS]`))
  }

  object `d_pull` extends Command("pull", "Pull an image or a repository from a registry") {

    object `all-tags` extends CommandOption("-a, --all-tags","""Download all tagged images in the repository""")

    object `disable-content-trust` extends CommandOption("--disable-content-trust","""Skip image verification (default true)""")

    object `quiet` extends CommandOption("-q, --quiet","""Suppress verbose output""")

    override val options = Some(List(`all-tags`, `disable-content-trust`, `quiet`))
    override val usage = Some("""docker pull [OPTIONS] NAME[:TAG|@DIGEST]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `NAME[:TAG|@DIGEST]` extends UsageDesc("""NAME[:TAG|@DIGEST]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `NAME[:TAG|@DIGEST]`))
  }

  object `d_push` extends Command("push", "Push an image or a repository to a registry") {

    object `disable-content-trust` extends CommandOption("--disable-content-trust","""Skip image signing (default true)""")

    override val options = Some(List(`disable-content-trust`))
    override val usage = Some("""docker push [OPTIONS] NAME[:TAG]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `NAME[:TAG]` extends UsageDesc("""NAME[:TAG]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `NAME[:TAG]`))
  }

  object `d_rename` extends Command("rename", "Rename a container") {

    override val options = Some(List())
    override val usage = Some("""docker rename CONTAINER NEW_NAME""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `NEW_NAME` extends UsageDesc("""NEW_NAME""")

    override val usageDesc = Some(List(`CONTAINER`, `NEW_NAME`))
  }

  object `d_restart` extends Command("restart", "Restart one or more containers") {

    object `time` extends CommandOption("-t, --time int","""Seconds to wait for stop before killing the container (default 10)""")

    override val options = Some(List(`time`))
    override val usage = Some("""docker restart [OPTIONS] CONTAINER [CONTAINER...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`, `[CONTAINER...]`))
  }

  object `d_rm` extends Command("rm", "Remove one or more containers") {

    object `force` extends CommandOption("-f, --force","""Force the removal of a running container (uses SIGKILL)""")

    object `link` extends CommandOption("-l, --link","""Remove the specified link""")

    object `volumes` extends CommandOption("-v, --volumes","""Remove the volumes associated with the container""")

    override val options = Some(List(`force`, `link`, `volumes`))
    override val usage = Some("""docker rm [OPTIONS] CONTAINER [CONTAINER...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`, `[CONTAINER...]`))
  }

  object `d_rmi` extends Command("rmi", "Remove one or more images") {

    object `force` extends CommandOption("-f, --force","""Force removal of the image""")

    object `no-prune` extends CommandOption("--no-prune","""Do not delete untagged parents""")

    override val options = Some(List(`force`, `no-prune`))
    override val usage = Some("""docker rmi [OPTIONS] IMAGE [IMAGE...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `IMAGE` extends UsageDesc("""IMAGE""")

    object `[IMAGE...]` extends UsageDesc("""[IMAGE...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `IMAGE`, `[IMAGE...]`))
  }

  object `d_run` extends Command("run", "Run a command in a new container") {

    object `add-host` extends CommandOption("--add-host list","""Add a custom host-to-IP mapping (host:ip)""")

    object `attach` extends CommandOption("-a, --attach list","""Attach to STDIN, STDOUT or STDERR""")

    object `blkio-weight` extends CommandOption("--blkio-weight uint16","""Block IO (relative weight), between 10 and 1000, or 0 to disable (default 0)""")

    object `blkio-weight-device` extends CommandOption("--blkio-weight-device list","""Block IO weight (relative device weight) (default [])""")

    object `cap-add` extends CommandOption("--cap-add list","""Add Linux capabilities""")

    object `cap-drop` extends CommandOption("--cap-drop list","""Drop Linux capabilities""")

    object `cgroup-parent` extends CommandOption("--cgroup-parent string","""Optional parent cgroup for the container""")

    object `cidfile` extends CommandOption("--cidfile string","""Write the container ID to the file""")

    object `cpu-period` extends CommandOption("--cpu-period int","""Limit CPU CFS (Completely Fair Scheduler) period""")

    object `cpu-quota` extends CommandOption("--cpu-quota int","""Limit CPU CFS (Completely Fair Scheduler) quota""")

    object `cpu-rt-period` extends CommandOption("--cpu-rt-period int","""Limit CPU real-time period in microseconds""")

    object `cpu-rt-runtime` extends CommandOption("--cpu-rt-runtime int","""Limit CPU real-time runtime in microseconds""")

    object `cpu-shares` extends CommandOption("-c, --cpu-shares int","""CPU shares (relative weight)""")

    object `cpus` extends CommandOption("--cpus decimal","""Number of CPUs""")

    object `cpuset-cpus` extends CommandOption("--cpuset-cpus string","""CPUs in which to allow execution (0-3, 0,1)""")

    object `cpuset-mems` extends CommandOption("--cpuset-mems string","""MEMs in which to allow execution (0-3, 0,1)""")

    object `detach` extends CommandOption("-d, --detach","""Run container in background and print container ID""")

    object `detach-keys` extends CommandOption("--detach-keys string","""Override the key sequence for detaching a container""")

    object `device` extends CommandOption("--device list","""Add a host device to the container""")

    object `device-cgroup-rule` extends CommandOption("--device-cgroup-rule list","""Add a rule to the cgroup allowed devices list""")

    object `device-read-bps` extends CommandOption("--device-read-bps list","""Limit read rate (bytes per second) from a device (default [])""")

    object `device-read-iops` extends CommandOption("--device-read-iops list","""Limit read rate (IO per second) from a device (default [])""")

    object `device-write-bps` extends CommandOption("--device-write-bps list","""Limit write rate (bytes per second) to a device (default [])""")

    object `device-write-iops` extends CommandOption("--device-write-iops list","""Limit write rate (IO per second) to a device (default [])""")

    object `disable-content-trust` extends CommandOption("--disable-content-trust","""Skip image verification (default true)""")

    object `dns` extends CommandOption("--dns list","""Set custom DNS servers""")

    object `dns-option` extends CommandOption("--dns-option list","""Set DNS options""")

    object `dns-search` extends CommandOption("--dns-search list","""Set custom DNS search domains""")

    object `domainname` extends CommandOption("--domainname string","""Container NIS domain name""")

    object `entrypoint` extends CommandOption("--entrypoint string","""Overwrite the default ENTRYPOINT of the image""")

    object `env` extends CommandOption("-e, --env list","""Set environment variables""")

    object `env-file` extends CommandOption("--env-file list","""Read in a file of environment variables""")

    object `expose` extends CommandOption("--expose list","""Expose a port or a range of ports""")

    object `gpus` extends CommandOption("--gpus gpu-request","""GPU devices to add to the container ('all' to pass all GPUs)""")

    object `group-add` extends CommandOption("--group-add list","""Add additional groups to join""")

    object `health-cmd` extends CommandOption("--health-cmd string","""Command to run to check health""")

    object `health-interval` extends CommandOption("--health-interval duration","""Time between running the check (ms|s|m|h) (default 0s)""")

    object `health-retries` extends CommandOption("--health-retries int","""Consecutive failures needed to report unhealthy""")

    object `health-start-period` extends CommandOption("--health-start-period duration","""Start period for the container to initialize before starting health-retries countdown (ms|s|m|h) (default 0s)""")

    object `health-timeout` extends CommandOption("--health-timeout duration","""Maximum time to allow one check to run (ms|s|m|h) (default 0s)""")

    object `help` extends CommandOption("--help","""Print usage""")

    object `hostname` extends CommandOption("-h, --hostname string","""Container host name""")

    object `init` extends CommandOption("--init","""Run an init inside the container that forwards signals and reaps processes""")

    object `interactive` extends CommandOption("-i, --interactive","""Keep STDIN open even if not attached""")

    object `ip` extends CommandOption("--ip string","""IPv4 address (e.g., 172.30.100.104)""")

    object `ip6` extends CommandOption("--ip6 string","""IPv6 address (e.g., 2001:db8::33)""")

    object `ipc` extends CommandOption("--ipc string","""IPC mode to use""")

    object `isolation` extends CommandOption("--isolation string","""Container isolation technology""")

    object `kernel-memory` extends CommandOption("--kernel-memory bytes","""Kernel memory limit""")

    object `label` extends CommandOption("-l, --label list","""Set meta data on a container""")

    object `label-file` extends CommandOption("--label-file list","""Read in a line delimited file of labels""")

    object `link` extends CommandOption("--link list","""Add link to another container""")

    object `link-local-ip` extends CommandOption("--link-local-ip list","""Container IPv4/IPv6 link-local addresses""")

    object `log-driver` extends CommandOption("--log-driver string","""Logging driver for the container""")

    object `log-opt` extends CommandOption("--log-opt list","""Log driver options""")

    object `mac-address` extends CommandOption("--mac-address string","""Container MAC address (e.g., 92:d0:c6:0a:29:33)""")

    object `memory` extends CommandOption("-m, --memory bytes","""Memory limit""")

    object `memory-reservation` extends CommandOption("--memory-reservation bytes","""Memory soft limit""")

    object `memory-swap` extends CommandOption("--memory-swap bytes","""Swap limit equal to memory plus swap: '-1' to enable unlimited swap""")

    object `memory-swappiness` extends CommandOption("--memory-swappiness int","""Tune container memory swappiness (0 to 100) (default -1)""")

    object `mount` extends CommandOption("--mount mount","""Attach a filesystem mount to the container""")

    object `name` extends CommandOption("--name string","""Assign a name to the container""")

    object `network` extends CommandOption("--network network","""Connect a container to a network""")

    object `network-alias` extends CommandOption("--network-alias list","""Add network-scoped alias for the container""")

    object `no-healthcheck` extends CommandOption("--no-healthcheck","""Disable any container-specified HEALTHCHECK""")

    object `oom-kill-disable` extends CommandOption("--oom-kill-disable","""Disable OOM Killer""")

    object `oom-score-adj` extends CommandOption("--oom-score-adj int","""Tune host's OOM preferences (-1000 to 1000)""")

    object `pid` extends CommandOption("--pid string","""PID namespace to use""")

    object `pids-limit` extends CommandOption("--pids-limit int","""Tune container pids limit (set -1 for unlimited)""")

    object `privileged` extends CommandOption("--privileged","""Give extended privileges to this container""")

    object `publish` extends CommandOption("-p, --publish list","""Publish a container's port(s) to the host""")

    object `publish-all` extends CommandOption("-P, --publish-all","""Publish all exposed ports to random ports""")

    object `read-only` extends CommandOption("--read-only","""Mount the container's root filesystem as read only""")

    object `restart` extends CommandOption("--restart string","""Restart policy to apply when a container exits (default "no")""")

    object `rm` extends CommandOption("--rm","""Automatically remove the container when it exits""")

    object `runtime` extends CommandOption("--runtime string","""Runtime to use for this container""")

    object `security-opt` extends CommandOption("--security-opt list","""Security Options""")

    object `shm-size` extends CommandOption("--shm-size bytes","""Size of /dev/shm""")

    object `sig-proxy` extends CommandOption("--sig-proxy","""Proxy received signals to the process (default true)""")

    object `stop-signal` extends CommandOption("--stop-signal string","""Signal to stop a container (default "15")""")

    object `stop-timeout` extends CommandOption("--stop-timeout int","""Timeout (in seconds) to stop a container""")

    object `storage-opt` extends CommandOption("--storage-opt list","""Storage driver options for the container""")

    object `sysctl` extends CommandOption("--sysctl map","""Sysctl options (default map[])""")

    object `tmpfs` extends CommandOption("--tmpfs list","""Mount a tmpfs directory""")

    object `tty` extends CommandOption("-t, --tty","""Allocate a pseudo-TTY""")

    object `ulimit` extends CommandOption("--ulimit ulimit","""Ulimit options (default [])""")

    object `user` extends CommandOption("-u, --user string","""Username or UID (format: [:])""")

    object `userns` extends CommandOption("--userns string","""User namespace to use""")

    object `uts` extends CommandOption("--uts string","""UTS namespace to use""")

    object `volume` extends CommandOption("-v, --volume list","""Bind mount a volume""")

    object `volume-driver` extends CommandOption("--volume-driver string","""Optional volume driver for the container""")

    object `volumes-from` extends CommandOption("--volumes-from list","""Mount volumes from the specified container(s)""")

    object `workdir` extends CommandOption("-w, --workdir string","""Working directory inside the container""")

    override val options = Some(List(`add-host`, `attach`, `blkio-weight`, `blkio-weight-device`, `cap-add`, `cap-drop`, `cgroup-parent`, `cidfile`, `cpu-period`, `cpu-quota`, `cpu-rt-period`, `cpu-rt-runtime`, `cpu-shares`, `cpus`, `cpuset-cpus`, `cpuset-mems`, `detach`, `detach-keys`, `device`, `device-cgroup-rule`, `device-read-bps`, `device-read-iops`, `device-write-bps`, `device-write-iops`, `disable-content-trust`, `dns`, `dns-option`, `dns-search`, `domainname`, `entrypoint`, `env`, `env-file`, `expose`, `gpus`, `group-add`, `health-cmd`, `health-interval`, `health-retries`, `health-start-period`, `health-timeout`, `help`, `hostname`, `init`, `interactive`, `ip`, `ip6`, `ipc`, `isolation`, `kernel-memory`, `label`, `label-file`, `link`, `link-local-ip`, `log-driver`, `log-opt`, `mac-address`, `memory`, `memory-reservation`, `memory-swap`, `memory-swappiness`, `mount`, `name`, `network`, `network-alias`, `no-healthcheck`, `oom-kill-disable`, `oom-score-adj`, `pid`, `pids-limit`, `privileged`, `publish`, `publish-all`, `read-only`, `restart`, `rm`, `runtime`, `security-opt`, `shm-size`, `sig-proxy`, `stop-signal`, `stop-timeout`, `storage-opt`, `sysctl`, `tmpfs`, `tty`, `ulimit`, `user`, `userns`, `uts`, `volume`, `volume-driver`, `volumes-from`, `workdir`))
    override val usage = Some("""docker run [OPTIONS] IMAGE [COMMAND] [ARG...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `IMAGE` extends UsageDesc("""IMAGE""")

    object `[COMMAND]` extends UsageDesc("""[COMMAND]""")

    object `[ARG...]` extends UsageDesc("""[ARG...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `IMAGE`, `[COMMAND]`, `[ARG...]`))
  }

  object `d_save` extends Command("save", "Save one or more images to a tar archive (streamed to STDOUT by default)") {

    object `output` extends CommandOption("-o, --output string","""Write to a file, instead of STDOUT""")

    override val options = Some(List(`output`))
    override val usage = Some("""docker save [OPTIONS] IMAGE [IMAGE...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `IMAGE` extends UsageDesc("""IMAGE""")

    object `[IMAGE...]` extends UsageDesc("""[IMAGE...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `IMAGE`, `[IMAGE...]`))
  }

  object `d_search` extends Command("search", "Search the Docker Hub for images") {

    object `filter` extends CommandOption("-f, --filter filter","""Filter output based on conditions provided""")

    object `format` extends CommandOption("--format string","""Pretty-print search using a Go template""")

    object `limit` extends CommandOption("--limit int","""Max number of search results (default 25)""")

    object `no-trunc` extends CommandOption("--no-trunc","""Don't truncate output""")

    override val options = Some(List(`filter`, `format`, `limit`, `no-trunc`))
    override val usage = Some("""docker search [OPTIONS] TERM""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `TERM` extends UsageDesc("""TERM""")

    override val usageDesc = Some(List(`[OPTIONS]`, `TERM`))
  }

  object `d_start` extends Command("start", "Start one or more stopped containers") {

    object `attach` extends CommandOption("-a, --attach","""Attach STDOUT/STDERR and forward signals""")

    object `detach-keys` extends CommandOption("--detach-keys string","""Override the key sequence for detaching a container""")

    object `interactive` extends CommandOption("-i, --interactive","""Attach container's STDIN""")

    override val options = Some(List(`attach`, `detach-keys`, `interactive`))
    override val usage = Some("""docker start [OPTIONS] CONTAINER [CONTAINER...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`, `[CONTAINER...]`))
  }

  object `d_stats` extends Command("stats", "Display a live stream of container(s) resource usage statistics") {

    object `all` extends CommandOption("-a, --all","""Show all containers (default shows just running)""")

    object `format` extends CommandOption("--format string","""Pretty-print images using a Go template""")

    object `no-stream` extends CommandOption("--no-stream","""Disable streaming stats and only pull the first result""")

    object `no-trunc` extends CommandOption("--no-trunc","""Do not truncate output""")

    override val options = Some(List(`all`, `format`, `no-stream`, `no-trunc`))
    override val usage = Some("""docker stats [OPTIONS] [CONTAINER...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `[CONTAINER...]`))
  }

  object `d_stop` extends Command("stop", "Stop one or more running containers") {

    object `time` extends CommandOption("-t, --time int","""Seconds to wait for stop before killing it (default 10)""")

    override val options = Some(List(`time`))
    override val usage = Some("""docker stop [OPTIONS] CONTAINER [CONTAINER...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`, `[CONTAINER...]`))
  }

  object `d_tag` extends Command("tag", "Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE") {

    override val options = Some(List())
    override val usage = Some("""docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]""")

    object `SOURCE_IMAGE[:TAG]` extends UsageDesc("""SOURCE_IMAGE[:TAG]""")

    object `TARGET_IMAGE[:TAG]` extends UsageDesc("""TARGET_IMAGE[:TAG]""")

    override val usageDesc = Some(List(`SOURCE_IMAGE[:TAG]`, `TARGET_IMAGE[:TAG]`))
  }

  object `d_top` extends Command("top", "Display the running processes of a container") {

    override val options = Some(List())
    override val usage = Some("""docker top CONTAINER [ps OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[ps` extends UsageDesc("""[ps""")

    object `OPTIONS]` extends UsageDesc("""OPTIONS]""")

    override val usageDesc = Some(List(`CONTAINER`, `[ps`, `OPTIONS]`))
  }

  object `d_unpause` extends Command("unpause", "Unpause all processes within one or more containers") {

    override val options = Some(List())
    override val usage = Some("""docker unpause CONTAINER [CONTAINER...]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`CONTAINER`, `[CONTAINER...]`))
  }

  object `d_update` extends Command("update", "Update configuration of one or more containers") {

    object `blkio-weight` extends CommandOption("--blkio-weight uint16","""Block IO (relative weight), between 10 and 1000, or 0 to disable (default 0)""")

    object `cpu-period` extends CommandOption("--cpu-period int","""Limit CPU CFS (Completely Fair Scheduler) period""")

    object `cpu-quota` extends CommandOption("--cpu-quota int","""Limit CPU CFS (Completely Fair Scheduler) quota""")

    object `cpu-rt-period` extends CommandOption("--cpu-rt-period int","""Limit the CPU real-time period in microseconds""")

    object `cpu-rt-runtime` extends CommandOption("--cpu-rt-runtime int","""Limit the CPU real-time runtime in microseconds""")

    object `cpu-shares` extends CommandOption("-c, --cpu-shares int","""CPU shares (relative weight)""")

    object `cpus` extends CommandOption("--cpus decimal","""Number of CPUs""")

    object `cpuset-cpus` extends CommandOption("--cpuset-cpus string","""CPUs in which to allow execution (0-3, 0,1)""")

    object `cpuset-mems` extends CommandOption("--cpuset-mems string","""MEMs in which to allow execution (0-3, 0,1)""")

    object `kernel-memory` extends CommandOption("--kernel-memory bytes","""Kernel memory limit""")

    object `memory` extends CommandOption("-m, --memory bytes","""Memory limit""")

    object `memory-reservation` extends CommandOption("--memory-reservation bytes","""Memory soft limit""")

    object `memory-swap` extends CommandOption("--memory-swap bytes","""Swap limit equal to memory plus swap: '-1' to enable unlimited swap""")

    object `pids-limit` extends CommandOption("--pids-limit int","""Tune container pids limit (set -1 for unlimited)""")

    object `restart` extends CommandOption("--restart string","""Restart policy to apply when a container exits""")

    override val options = Some(List(`blkio-weight`, `cpu-period`, `cpu-quota`, `cpu-rt-period`, `cpu-rt-runtime`, `cpu-shares`, `cpus`, `cpuset-cpus`, `cpuset-mems`, `kernel-memory`, `memory`, `memory-reservation`, `memory-swap`, `pids-limit`, `restart`))
    override val usage = Some("""docker update [OPTIONS] CONTAINER [CONTAINER...]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`[OPTIONS]`, `CONTAINER`, `[CONTAINER...]`))
  }

  object `d_version` extends Command("version", "Show the Docker version information") {

    object `format` extends CommandOption("-f, --format string","""Format the output using the given Go template""")

    object `kubeconfig` extends CommandOption("--kubeconfig string","""Kubernetes config file""")

    override val options = Some(List(`format`, `kubeconfig`))
    override val usage = Some("""docker version [OPTIONS]""")

    object `[OPTIONS]` extends UsageDesc("""[OPTIONS]""")

    override val usageDesc = Some(List(`[OPTIONS]`))
  }

  object `d_wait` extends Command("wait", "Block until one or more containers stop, then print their exit codes") {

    override val options = Some(List())
    override val usage = Some("""docker wait CONTAINER [CONTAINER...]""")

    object `CONTAINER` extends UsageDesc("""CONTAINER""")

    object `[CONTAINER...]` extends UsageDesc("""[CONTAINER...]""")

    override val usageDesc = Some(List(`CONTAINER`, `[CONTAINER...]`))
  }

  val all: Map[String, Command] = Map(("builder" -> `d_builder`), ("config" -> `d_config`), ("container" -> `d_container`), ("context" -> `d_context`), ("image" -> `d_image`), ("network" -> `d_network`), ("node" -> `d_node`), ("plugin" -> `d_plugin`), ("secret" -> `d_secret`), ("service" -> `d_service`), ("stack" -> `d_stack`), ("swarm" -> `d_swarm`), ("system" -> `d_system`), ("trust" -> `d_trust`), ("volume" -> `d_volume`), ("attach" -> `d_attach`), ("build" -> `d_build`), ("commit" -> `d_commit`), ("cp" -> `d_cp`), ("create" -> `d_create`), ("diff" -> `d_diff`), ("events" -> `d_events`), ("exec" -> `d_exec`), ("export" -> `d_export`), ("history" -> `d_history`), ("images" -> `d_images`), ("import" -> `d_import`), ("info" -> `d_info`), ("inspect" -> `d_inspect`), ("kill" -> `d_kill`), ("load" -> `d_load`), ("login" -> `d_login`), ("logout" -> `d_logout`), ("logs" -> `d_logs`), ("pause" -> `d_pause`), ("port" -> `d_port`), ("ps" -> `d_ps`), ("pull" -> `d_pull`), ("push" -> `d_push`), ("rename" -> `d_rename`), ("restart" -> `d_restart`), ("rm" -> `d_rm`), ("rmi" -> `d_rmi`), ("run" -> `d_run`), ("save" -> `d_save`), ("search" -> `d_search`), ("start" -> `d_start`), ("stats" -> `d_stats`), ("stop" -> `d_stop`), ("tag" -> `d_tag`), ("top" -> `d_top`), ("unpause" -> `d_unpause`), ("update" -> `d_update`), ("version" -> `d_version`), ("wait" -> `d_wait`))
}
