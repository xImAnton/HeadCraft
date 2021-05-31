
if __name__ == '__main__':
    mats = []
    with open("materials.txt", "r") as f:
        for line in f.readlines():
            line = line.strip()
            if line.startswith(" ") or line.startswith("/") or line == "\n" or line.startswith("\\"):
                continue
            if "(" not in line:
                mats.append(line)
                continue
            mats.append(line[:line.index("(")])

    with open("materials.txt", "w") as f:
        f.write("\n".join(mats))
