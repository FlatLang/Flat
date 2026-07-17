{
  description = "Flat development environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs =
    {
      self,
      nixpkgs,
      flake-utils,
    }:
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs { inherit system; };
      in
      {
        devShells.default = pkgs.mkShell {
          buildInputs = with pkgs; [
            maven
            jdk21_headless
            direnv
            nushell
          ];

          shellHook = ''
            echo "Flat development environment loaded"
            echo "Available tools:"
            echo "  - java ($(java -version 2>&1 | head -1))"
            echo "  - maven ($(mvn --version | head -1))"
            echo "  - direnv ($(direnv version))"
            echo "  - nu ($(nu --version))"
            echo ""

            # Only exec Nushell for interactive shells, not `nix develop -c ...`.
            if [ -z "$IN_NIX_SHELL_NU" ] && [ -z "$BASH_EXECUTION_STRING" ]; then
              case "$-" in
                *i*) export IN_NIX_SHELL_NU=1; exec nu ;;
              esac
            fi
          '';
        };
      }
    );
}
