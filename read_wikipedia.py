import wikipedia as wiki

mpls = wiki.page("Minneaplis")

link = mpls.url
title = mpls.title


content = mpls.content

print("Link (URL) -> "+link + "\n")
print("Title -> " +title + "\n\n")

print("\t\t"+content)
