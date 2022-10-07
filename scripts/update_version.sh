# Copyright (C) (2022) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
#
# This file is part of Ntro
#
# Ntro is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# Ntro is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

##### INCLUDE #####
this_dir=$(readlink -f $0)
scripts_dir=$(dirname "$this_dir")
. "$scripts_dir/include.sh"
###################

old_version=$1
new_version=$2

if [ "$old_version" = "" -o "$new_version" = "" ]; then
    echo "usage: $0 old_version new_version"
    exit
fi


save_dir

cd "$root_dir"

ag -a "'$old_version'" -l | while read i; do sed "s/'$old_version'/'$new_version'/" -i $i; done

sh "$scripts_dir"/commit.sh "$new_version"

sh "$scripts_dir"/tag.sh "$new_version"

restore_dir
