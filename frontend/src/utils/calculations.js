export const calculateOvumCount = (humanCount, caste) => {
    if (["Alpha", "Beta"].includes(caste))
        return humanCount;
    if (["Gamma", "Delta", "Epsilon"].includes(caste))
        return Math.ceil(humanCount / 72);
};