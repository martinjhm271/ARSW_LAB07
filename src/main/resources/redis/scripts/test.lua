-- test.lua
local current = ARGV[3]
local word = ARGV[2]

for i = 1, #word do
    local c = word:sub(i,i)
    if c == ARGV[1]
	then
        current=current:sub(1, i-1)..c..current:sub(i+1)
    end
end

return current