function change(amount)
  if math.type(amount) ~= "integer" then
    error("Amount must be an integer")
  end
  if amount < 0 then
    error("Amount cannot be negative")
  end
  local counts, remaining = {}, amount
  for _, denomination in ipairs({25, 10, 5, 1}) do
    counts[denomination] = remaining // denomination
    remaining = remaining % denomination
  end
  return counts
end

-- Write your first then lower case function here
function first_then_lower_case(table, condition)
    for _, value in ipairs(table) do
        if condition(value) then
            return string.lower(value)
        end
    end
    return nil
end

-- Write your powers generator here
function powers_generator(base, limit)
    return coroutine.create(function()
        local power = 1
        while power < limit do
            coroutine.yield(power)
            power = power * base
        end
    end)
end

-- Write your say function here
function say(word)
    local words = {}    
    local function addToSentence(next_word)
        if next_word == nil then
            return table.concat(words, " ")
        else
            table.insert(words, next_word)
            return addToSentence
        end
    end
    if word == nil then
        return addToSentence()
    else
        table.insert(words, word)
        return addToSentence
    end
end

-- Write your line count function here
function meaningful_line_count(filename)
    local file, err = io.open(filename, "r")
    if not file then
        error("No such file: " .. filename)
    end
    local count = 0
    for line in file:lines() do
      -- effectively trims leading and trailing whitespace from line, leaving just the core content of the string. I used chatGPT to help me figure this part out. 
        line = line:gsub("^%s*(.-)%s*$", "%1")
        if line ~= "" and not line:match("^#") then
            count = count + 1
        end
    end
    file:close()
    return count
end

-- Write your Quaternion table here
